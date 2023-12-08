package com.gorge.smash.rest.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gorge.smash.model.entity.Text;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.TextRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/text")
public class TextController extends RestControllerBase {

	@PostConstruct
	public void init() {
		LOGGER = LoggerFactory.getLogger(TextController.class);
	}

	@Autowired
	TextRepository textRepo;


	@Operation(summary = "Set Admin Text")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Text setAdminText(@RequestBody String text) throws GorgePasContentException {
		List<Text> lst = textRepo.findAll();
		Text txt = new Text();
		if(lst != null && !lst.isEmpty()) {
			txt = lst.get(0);
		}
		txt.setText(text);
		return textRepo.save(txt);
	}

	@Operation(summary = "Get Admin Text")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Text getAdminText() throws GorgePasContentException {
		List<Text> lst = textRepo.findAll();
		if(lst != null && !lst.isEmpty())
			return lst.get(0);
		
		return null;
	}
}
