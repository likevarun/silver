package com.rest.silver.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.silver.dao.Silver;
import com.rest.silver.dao.SilverService;
import com.rest.silver.model.SilverPojo;
import com.rest.silver.util.CustomErrorType;

@RestController
@RequestMapping("")
public class SilverRestController {

	public static final Logger logger = LoggerFactory.getLogger(SilverRestController.class);
	@Autowired
	SilverService silverService;

	// ------------------- GET ---------------------------------------------

	@RequestMapping(value = "/state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getState(HttpServletRequest request) {
		logger.info("GET state: Silver");
		try {
			Silver user = silverService.checkUser(request.getHeader("User-Agent"));
			logger.info("Retrieved State Value:" + user.getState());
			return new ResponseEntity<String>(user.getState(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/sum", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getSum(HttpServletRequest request) {
		logger.info("GET sum: Silver");
		try {
			Silver user = silverService.checkUser(request.getHeader("User-Agent"));
			return new ResponseEntity<String>(String.valueOf(silverService.getSum(user.getUserId())), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/chars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getChars(HttpServletRequest request) {
		logger.info("GET chars: Silver");
		try {
			Silver user = silverService.checkUser(request.getHeader("User-Agent"));
			return new ResponseEntity<String>(silverService.getChars(user.getUserId()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// ------------------- Post -------------------------------------------

	@RequestMapping(value = "/chars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> postMessage(@RequestBody SilverPojo silver, HttpServletRequest request,
			UriComponentsBuilder ucBuilder) {
		logger.info("Post chars: Silver {}", silver);
		Silver sil, user = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			user = silverService.checkUser(request.getHeader("User-Agent"));
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
	public ResponseEntity<Map<String, String>> deleteMessage(@PathVariable char character, HttpServletRequest request,
			UriComponentsBuilder ucBuilder) {
		logger.info("Delete char: Silver {}", character);
		Map<String, String> map = new HashMap<String, String>();
		Silver user = null;
		try {
			user = silverService.checkUser(request.getHeader("User-Agent"));
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