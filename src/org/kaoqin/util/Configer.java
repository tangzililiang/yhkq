package org.kaoqin.util;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.kaoqin.vo.User;

/**
 * 参数配置
 * 
 * @author tang
 * 
 */
public class Configer {
	public class Const {
		public static final String SYS_CONFIG_PATH = "data/config.properties";
		public static final String USER_CONFIG_PATH = "data/users.properties";
		public static final String SPLIT_CHAR = "\\.";// 分隔符
		public static final String DEFAULT_CONTENT_CHARSET = "gb2312";
                public static final String VERSION = "YH考勤工具 V1.1";//
                public static final String DEFAULT_PASSWORD = "123456";//默认密码
		//ASP.NET隐藏域
		public static final String EVENTVALIDATION = "__EVENTVALIDATION";
		public static final String VIEWSTATE = "__VIEWSTATE";
                
	}

	private static Map config = new HashMap();
	private static Map<String, User> userMap = new HashMap<String, User>();
	// 初始化读取配置
	static {
		load();
	}

	/**
	 * 从Map中获取配置信息 并转换为String类型，若key不存在，则抛出异常
	 * 
	 * @param key
	 *            key
	 * @return value value
	 */
	public static String getProporty(String key) {
		Object value = config.get(key);
		if (value == null || "".equals(String.valueOf(value))) {
			throw new IllegalArgumentException("参数[" + key + "]不存在或为空!");
		}
		return String.valueOf(value).trim();
	}
        /**
	 * 从Map中获取配置信息 并转换为String类型，若key不存在，返回“”，不抛出异常
	 * 
	 * @param key
	 *            key
	 * @return value value
	 */
	public static String getProportyIgnor(String key) {
		Object value = config.get(key);
		if (value == null || "".equals(String.valueOf(value))) {
			return "";
		}
		return String.valueOf(value).trim();
	}
        /**
	 * 设置配置信息
	 * 
	 * @param key
	 *            key
	 * @param value 
         *            value
	 */
	public static void setProporty(String key,String value) {
		if (StringUtils.isEmpty(key)||StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("参数[" + key + "]不能为空!");
		}
		config.put(key, value);
	}

	/**
	 * 从Map中获取用户 若用户不存在，则抛出异常
	 * 
	 * @param username
	 *            考勤系统
	 * @return User
	 */
	public static User getUser(String username) {
		User user = userMap.get(username);
		if (user == null) {
			throw new IllegalArgumentException("用户[" + username + "]不存在!");
		}
		return user;
	}
        /**
	 * 添加用户
	 * 
	 * @param user
	 *           user
	 */
	public static void addUser(User user) {
            if (user == null) {
		throw new IllegalArgumentException("用户对象不能为空!");
	    }
            if (StringUtils.isEmpty(user.getUsername())) {
		throw new IllegalArgumentException("用户名不能为空!");
	    }
            if (StringUtils.isEmpty(user.getPassword())) {
		throw new IllegalArgumentException("密码不能为空!");
	    }
            if (StringUtils.isEmpty(user.getYzm())) {
		throw new IllegalArgumentException("验证码不能为空!");
	    }
             if (StringUtils.isEmpty(user.getYgyzm())) {
		throw new IllegalArgumentException("员工验证码不能为空!");
	    }
            if (StringUtils.isEmpty(user.getIp())) {
		throw new IllegalArgumentException("IP不能为空!");
	    }
            if (StringUtils.isEmpty(user.getMacaddress())) {
		throw new IllegalArgumentException("MAC不能为空!");
	    }
            if (StringUtils.isEmpty(user.getSeriousnumber())) {
		throw new IllegalArgumentException("序列号不能为空!");
	    }
	    userMap.put(user.getUsername(),user);
	}

	/**
	 * 获取所有用户
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<User> getAllUser() {
		return new ArrayList(userMap.values());
	}
        
        /**
	 * 加载配置
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static void load() {
		InputStream in = null;
		try {
			// 系统配置
			Properties properties = new Properties();
			in = new BufferedInputStream(new FileInputStream(
					Configer.Const.SYS_CONFIG_PATH));
			properties.load(in);
			config.putAll(properties);

			// 用户配置
			properties = new Properties();
			in = new BufferedInputStream(new FileInputStream(
					Configer.Const.USER_CONFIG_PATH));
			properties.load(in);
			Map tempUserMap = new HashMap();
			tempUserMap.putAll(properties);
			Iterator<String> iterator = tempUserMap.keySet().iterator();
			while (iterator.hasNext()) {// 处理user
				String key = iterator.next().trim();
				String username = key.split(Const.SPLIT_CHAR)[0];
				String attr = key.split(Const.SPLIT_CHAR)[1];
				String value = String.valueOf(tempUserMap.get(key)).trim();
				Method methodSet = User.class.getMethod("set"
						+ StringUtils.capitalize(attr), String.class);
				if (!userMap.containsKey(username)) {
					userMap.put(username, new User());
					userMap.get(username).setUsername(username);
				}
				User user = userMap.get(username);
				methodSet.invoke(user, value);
			}
			Map users = userMap;
		} catch (Exception e) {
			throw new RuntimeException("配置文件读取错误！", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception xe) {
				xe.printStackTrace();
			}

		}
	}
        
        /**
	 * 保存配置信息
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static void persistent() {
		OutputStream out = null;
		try {
                        //系统配置
                        Properties properties = new Properties();
                        properties.putAll(config);
			out = new FileOutputStream(new File(Const.SYS_CONFIG_PATH));
                        properties.store(out, "sys config");
                        //用户配置
                        properties = new Properties();
                        Iterator<User> useriterator=getAllUser().iterator();
                        Method[] methods=User.class.getMethods();
                        while(useriterator.hasNext()){
                            User user=useriterator.next();
                            String username=user.getUsername();
                            for(int i=0;i<methods.length;i++){
                                if(methods[i].getName().startsWith("get")&&(!methods[i].getName().contains("Class"))){
                                    String value=String.valueOf(methods[i].invoke(user));
                                    String attr=methods[i].getName().substring(3).toLowerCase();
                                    properties.put(username+"."+attr, value);
                                }
                            }
                        }
                        out = new FileOutputStream(new File(Const.USER_CONFIG_PATH));
                        properties.store(out, "user config");
		} catch (Exception e) {
			throw new RuntimeException("配置信息保存错误！", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception xe) {
				xe.printStackTrace();
			}

		}
                //重新加载配置信息
                load();
	}
        /**
         * 清除内存中的配置信息
         */
        public static void clear() {
            config.clear();
            userMap.clear();
        }

}
