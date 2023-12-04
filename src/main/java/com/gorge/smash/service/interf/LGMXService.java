
package com.gorge.smash.service.interf;

import java.util.List;
import java.util.Map;

import com.gorge.smash.model.entity.LGMXSong;
import com.gorge.smash.rest.exception.GorgePasContentException;

public interface LGMXService
{
	public Map<String, String> makeAndUploadPDF(List<LGMXSong> songs) throws GorgePasContentException;
	
    public Map<String, String> makeFullPDF(List<LGMXSong> songs) throws GorgePasContentException;
}
