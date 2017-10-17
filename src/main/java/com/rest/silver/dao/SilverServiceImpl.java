package com.rest.silver.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.silver.model.SilverPojo;
import com.rest.silver.util.CustomErrorType;

import eu.bitwalker.useragentutils.UserAgent;

@Service("silverService")
public class SilverServiceImpl implements SilverService{
	public static final Logger logger = LoggerFactory.getLogger(SilverServiceImpl.class);

	@Autowired
	SilverRepository silverRepo;

	public Silver checkUser(String requestHeader) {
		logger.info("RequestHeader: "+requestHeader);
		UserAgent userAgent = UserAgent.parseUserAgentString(requestHeader);
		String userId = String.valueOf(userAgent.getBrowser().getId())
				+ String.valueOf(userAgent.getOperatingSystem().getId());
		Silver user = fetchUser(userId);
		if (user == null) {
			logger.info("This UserId-" + userId + " doesn't exist in database, so create new user");
			return createUser(userId);
		}
		return user;
	}

	public String getState(String userId) {
		logger.info("Fetching state of userId:"+userId);
		return silverRepo.findOne(userId).getState();
	}

	public int getSum(String userId) {
		String str = getState(userId);
		String[] part = str.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
		int sum = 0;
		for (String s : part) {
			if (s.matches("\\d+")) {
				sum += Integer.parseInt(s);
			}
		}
		logger.info("UserId:"+userId+" Sum:"+sum);
		return sum;
	}

	public String getChars(String userId) {
		String str = getState(userId);
		String[] part = str.split("[A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
		String chars = "";
		for (String s : part) {
			if (s.matches("\\w+")) {
				chars += s;
			}
		}
		logger.info("UserId:"+userId+" chars:"+chars);
		return chars;
	}

	public Silver postState(String userId, SilverPojo s) throws CustomErrorType, Exception{
		if (s.getCharacter().length() != 1) {
			logger.error(s.getCharacter()+" - Not a single alphanumeric character");
			throw new CustomErrorType(s.getCharacter()+" - Not a single alphanumeric character");
		}
		if (Integer.parseInt(s.getAmount()) > 9 || Integer.parseInt(s.getAmount()) < 1) {
			logger.error(s.getAmount()+" - Number doesn't lie between range 1 - 9");
			throw new CustomErrorType(s.getAmount()+" - Number doesn't lie between range 1 - 9");
		}
		Silver sil = null;
		try {
			char c = s.getCharacter().charAt(0);
			String str = silverRepo.findOne(userId).getState();
			for (int i = 0; i < Integer.parseInt(s.getAmount()); i++) {
				str += c;
			}
			sil = silverRepo.findOne(userId);
			sil.setState(str);
			if(str.length() > 200){
				logger.error("Not a single alphanumeric character");
				throw new CustomErrorType("Not a single alphanumeric character");
			}
			sil = silverRepo.save(sil);
			logger.info("UserId:"+sil.getUserId() + " added:"+c+" "+s.getAmount()+" times");
			logger.info("State value: "+sil.getState());
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof CustomErrorType)
				throw new CustomErrorType(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return sil;
	}

	public boolean deleteChars(String userId, char c) throws CustomErrorType{
		logger.info("Delete char:"+c+" from userId:"+userId);
		if(!Character.isLetterOrDigit(c)){
			logger.error(c+" - Not a alphanumeric character");
			throw new CustomErrorType(c+" - Not a alphanumeric character");
		}
		String str = getState(userId);
		int ind = str.lastIndexOf(c);
		if (ind >= 0) {
			str = new StringBuilder(str).replace(ind, ind + 1, "").toString();
			Silver sil = silverRepo.findOne(userId);
			sil.setState(str);
			sil = silverRepo.save(sil);
			logger.info("UserId:"+userId+" deleted char:"+c+", State:"+sil.getState());
			return true;
		} else
			return false;
	}

	public Silver fetchUser(String userId) {
		logger.info("SilverService: fetchUser with userId:" + userId);
		return silverRepo.findOne(userId);
	}

	public Silver createUser(String userId) {
		logger.info("SilverService: createUser with userId:" + userId);
		Silver silver = new Silver();
		silver.setUserId(userId);
		silver.setState("");
		silver = silverRepo.save(silver);
		logger.info("SilverService: User created with userId:" + silver.getUserId());
		return silver;
	}
}
