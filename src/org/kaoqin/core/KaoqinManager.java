package org.kaoqin.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.kaoqin.util.Configer;
import org.kaoqin.util.KqLoger;
import org.kaoqin.vo.User;

/**
 * 自动化考勤入口
 * 
 * @author lenovo
 */
public class KaoqinManager {
       /**
        * 定时器,程序x分钟检查一次，如果当前时间在上班时间之前或下班时间之后，且与上下班时间相差30分钟以内，则自动考勤
        */
       private static Timer timer;
	/**
         * 启动自动考勤
         */
	public static void startup() {
		KqLoger.log("考勤计时器已就绪！");
		KqLoger.log("之后"+Configer.getProporty("checkInterval") +"分钟检查一次，如果当前时间在上班时间之前或下班时间之后，且与上下班时间相差30分钟以内，则自动考勤...");
		
		timer = new Timer();
		int minute = Integer.parseInt(Configer.getProporty("checkInterval"));
		long millisecond = 1000 * 60 * minute;//x分钟检查一次
		timer.schedule(new TimerTask() {
			public void run() {
				if (beReady()) {
					runKaoqinForAllUsers();
				}
			}
		}, millisecond, millisecond);

	}

	/**
	 * 为每个用户开启一个新线程执行考勤
	 * 
	 * @return
	 */
	public static void runKaoqinForAllUsers() {
                KqLoger.log("新的一次考勤开始.");
		Iterator<User> userIterator = Configer.getAllUser().iterator();
		while (userIterator.hasNext()) {
			User user = userIterator.next();
			new Thread(new KaoqinTask(user)).start();
		}
	}
	
	/**
	 * 判断当前时间是否执行考勤
	 * 
	 * @return
	 */
	public static boolean beReady() {
		try {
			// 获取上下班时间
			Date sbDate = new SimpleDateFormat("hh:mm:ss").parse(Configer
					.getProporty("sbTime"));
			Calendar sbcal = Calendar.getInstance();// 上班时间
			sbcal.set(Calendar.HOUR_OF_DAY, sbDate.getHours());
			sbcal.set(Calendar.MINUTE, sbDate.getMinutes());
			Date xbDate = new SimpleDateFormat("hh:mm:ss").parse(Configer
					.getProporty("xbTime"));
			Calendar xbcal = Calendar.getInstance();// 下班时间
			xbcal.set(Calendar.HOUR_OF_DAY, xbDate.getHours());
			xbcal.set(Calendar.MINUTE, xbDate.getMinutes());

			// 如果当前时间在上班时间之前或下班时间之后且与上下班时间相差30分钟以内，则返回true
			long millisecond = 1000 * 60 * 30;// 30分钟
			Calendar cal = Calendar.getInstance();// 当前时间
			return (cal.before(sbcal) || cal.after(xbcal))
					&& (Math.abs(cal.getTime().getTime()
							- sbcal.getTime().getTime()) < millisecond || Math
							.abs(cal.getTime().getTime()
									- xbcal.getTime().getTime()) < millisecond);
		} catch (Exception e) {
			throw new RuntimeException("上下班时间处理异常" , e);
		}
	}
        /**
         * 终止计时器
         */
        public static void stop(){
            if(timer!=null){
                timer.cancel();
            }
        }
}
