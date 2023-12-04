package com.gorge.smash.rest.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gorge.smash.model.entity.LGMXSong;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.LGMXSongRepository;
import com.gorge.smash.service.interf.LGMXService;
import com.gorge.smash.util.Constant;
import com.gorge.smash.util.MonoText;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/lgmx")
public class LGMXController extends RestControllerBase {

	@PostConstruct
	public void init() {
		LOGGER = LoggerFactory.getLogger(LGMXController.class);
	}

	@Autowired
	LGMXSongRepository LGMXRepo;

	@Autowired
	LGMXService LGMXService;

	@Operation(summary = "Add an LGMX Song in DB")
	@RequestMapping(path = "/songs/add", method = RequestMethod.POST)
	public LGMXSong addSong(@RequestBody LGMXSong song) throws GorgePasContentException {
		if (LGMXRepo.existsByTitle(song.getTitle()))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This button already exists");
		return LGMXRepo.save(song);
	}

	@Operation(summary = "Add an LGMX Song in DB")
	@RequestMapping(path = "/songs/add/all", method = RequestMethod.POST)
	public List<LGMXSong> addSongs(@RequestBody List<LGMXSong> songs) throws GorgePasContentException {
		List<LGMXSong> results = Collections.emptyList();
		songs.forEach((LGMXSong s) -> {
			if (!LGMXRepo.existsByTitle(s.getTitle())) {
				LGMXRepo.save(s);
			}
		});

		return results;
	}

	@Operation(summary = "Get all LGMX Songs")
	@RequestMapping(path = "/songs", method = RequestMethod.GET)
	public List<LGMXSong> getLGMXSongs() throws GorgePasContentException {
		return LGMXRepo.findAll();
	}

	@Operation(summary = "Get a LGMX Song by title")
	@RequestMapping(path = "/songs/title/{title}", method = RequestMethod.GET)
	public LGMXSong getLGMXSongByTitle(@PathVariable("title") String title) throws GorgePasContentException {
		return LGMXRepo.findByTitle(title);
	}

	@Operation(summary = "Get an LGMX Songs by id")
	@RequestMapping(path = "/songs/{id}", method = RequestMethod.GET)
	public LGMXSong getLGMXSongById(@PathVariable("id") Long id) throws GorgePasContentException {
		return LGMXRepo.findById(id).orElse(null);
	}

	@Operation(summary = "Get all LGMX Songs")
	@RequestMapping(path = "/songs/{id}", method = RequestMethod.DELETE)
	public void deleteLGMXSong(@PathVariable("id") Long id) throws GorgePasContentException {
		LGMXRepo.deleteById(id);
	}

	@Operation(summary = "Get all LGMX Songs")
	@RequestMapping(path = "/songs/conduite/text", method = RequestMethod.POST)
	public Map<String, String> makeConduite(@RequestBody MonoText txt) throws GorgePasContentException {
		List<LGMXSong> results = new ArrayList<LGMXSong>();
		
		String setlist = txt.getText();
		
		if (setlist.contains(Constant.PLEUH_OPENER)) {
			setlist = setlist.replace(Constant.PLEUH_OPENER, "&");
			List<String> list = Arrays.asList(setlist.split("&"));
			list.forEach(title -> {
				if (title != null && title != "" && title.length() > 3) {
					Integer index = list.indexOf(title);
					title = title.contains("(") ? title.substring(0, title.indexOf('(') - 1).trim() : title.trim();
					LGMXSong song = LGMXRepo.findByTitle(title);
					if (title.contains(Constant.RAPPEL) || song == null) {
						song = new LGMXSong(title);
					}
					song.setOrder(index);
					results.add(song);
				}
			});

			results.sort((o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));

			return LGMXService.makeFullPDF(results);

		} else {
			throw new GorgePasContentException(HttpStatus.BAD_REQUEST,
					"Ta setlist n'est pas formattée comme celles de Pleuh !!");
		}
	}

	@Operation(summary = "Get all LGMX Songs")
	@RequestMapping(path = "/songs/conduite/list", method = RequestMethod.POST)
	public Map<String, String> makeConduite(@RequestBody List<String> list) throws GorgePasContentException {
		List<LGMXSong> results = new ArrayList<LGMXSong>();
		list.forEach((String t) -> {
			LGMXSong song = LGMXRepo.findByTitle(t);
			song.setOrder(list.indexOf(t));
			results.add(song);
		});

		results.sort((o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));

		return LGMXService.makeFullPDF(results);
	}

	@Operation(summary = "Get all LGMX Songs")
	@RequestMapping(path = "/files/{name}", method = RequestMethod.GET)
	public ResponseEntity<Object> getFile(@PathVariable("name") String filename)
			throws GorgePasContentException, IOException {
		String decoded = null;
		try {
			decoded = URLDecoder.decode(filename, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (decoded == null)
			throw new GorgePasContentException(HttpStatus.BAD_REQUEST, "Mauvais fichier !");

		// Set the content type and attachment disposition
		String contentType = "application/pdf";
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + decoded);
		Path path = Paths.get(Constant.THERE + decoded);
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {

		}
		MultipartFile fileTOSend = new MockMultipartFile(decoded, decoded, contentType, content);

		return ResponseEntity.ok().headers(headers).contentLength(content.length)
				.contentType(org.springframework.http.MediaType.parseMediaType(contentType)).body(content);
	}

}
