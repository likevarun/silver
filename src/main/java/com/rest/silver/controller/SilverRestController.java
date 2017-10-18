package com.rest.silver.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.silver.dao.Silver;
import com.rest.silver.dao.SilverService;
import com.rest.silver.model.SilverPojo;
import com.rest.silver.util.CustomErrorType;

/**
 * Silver REST controller facilitating GET, POST, DELETE requests
 * 
 * @author likevarun
 *
 */
@RestController
@RequestMapping("")
public class SilverRestController {

	public static final Logger logger = LoggerFactory.getLogger(SilverRestController.class);

	@Autowired
	private SilverService silverService;

	// ------------------- GET ---------------------------------------------

	@RequestMapping(value = "/state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> getState(@RequestParam("userId") String token) {
		logger.info("GET state: Silver");
		Map<String, String> map = new HashMap<String, String>();
		Silver user =  null;
		try {
			user = silverService.checkUser(token);
			map.put(user.getUserId(), user.getState());
			logger.info("Retrieved State Value:" + user.getState());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} catch (Exception e) {
			map.put(user.getUserId(), e.getMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/sum", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> getSum(@RequestParam("userId") String token) {
		logger.info("GET sum: Silver");
		Map<String, String> map = new HashMap<String, String>();
		Silver user = null;
		try {
			user = silverService.checkUser(token);
			map.put(user.getUserId(), String.valueOf(silverService.getSum(user.getUserId())));
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} catch (Exception e) {
			map.put(user.getUserId(), e.getMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/chars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> getChars(@RequestParam("userId") String token) {
		logger.info("GET chars: Silver");
		Map<String, String> map = new HashMap<String, String>();
		Silver user = null;
		try {
			user = silverService.checkUser(token);
			map.put(user.getUserId(), silverService.getChars(user.getUserId()));
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} catch (Exception e) {
			map.put(user.getUserId(), e.getMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		}
	}

	// ------------------- Post -------------------------------------------

	@RequestMapping(value = "/chars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> postChars(@RequestParam("userId") String token,
			@RequestBody SilverPojo silver, UriComponentsBuilder ucBuilder) {
		logger.info("Post chars: Silver {}", silver);
		Silver sil, user = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			user = silverService.checkUser(token);
			sil = silverService.postState(user.getUserId(), silver);
			if (sil != null) {
				map.put(sil.getUserId(), sil.getState());
				return new ResponseEntity<Map<String, String>>(map, HttpStatus.CREATED);
			}
			map.put(user.getUserId(), user.getState());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		} catch (CustomErrorType e) {
			map.put(user.getUserId(), e.getErrorMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			map.put(user.getUserId(), e.getMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		}
	}

	// ------------------- Delete ------------------------------------------

	@RequestMapping(value = "/chars/{character}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> deleteChars(@RequestParam("userId") String token,
			@PathVariable char character, UriComponentsBuilder ucBuilder) {
		logger.info("Delete char: Silver {}", character);
		Map<String, String> map = new HashMap<String, String>();
		Silver user = null;
		try {
			user = silverService.checkUser(token);
			if (silverService.deleteChars(user.getUserId(), character)) {
				map.put(user.getUserId(), silverService.getState(user.getUserId()));
				return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
			}
			map.put(user.getUserId(), silverService.getState(user.getUserId()));
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		} catch (CustomErrorType e) {
			map.put(user.getUserId(), e.getErrorMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			map.put(user.getUserId(), e.getMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		}
	}
}