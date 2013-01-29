package org.kaoqin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.kaoqin.ui.MainFrame;

/**
 * 用于输出和保存考勤日志
 * @author lenovo
 */
public class KqLoger {
        private static MainFrame mainFrame;//输出窗口
	private static Logger logger = Logger.getLogger(KqLoger.class);
        private static SimpleDateFormat formate=new SimpleDateFormat("HH:mm:ss"); 
	/**
         * 输出和保存日志
         * @param msg 
         */
        public static void log(String msg){
		logger.info(msg);
                msg="["+formate.format(new Date())+"]" +msg+"\r\n";
                mainFrame.jTextAreaMsg.append(msg);
                mainFrame.jTextAreaMsg.setCaretPosition(mainFrame.jTextAreaMsg.getText().length());
                //当窗口输出超过1000行时，清空
                if(1000<mainFrame.jTextAreaMsg.getLineCount()){
                    mainFrame.jTextAreaMsg.setText("输出已超过一千行，清空输出，详细日志信息请查看日志文件 \r\n");
                }
	}
        public static void setMainFrame(MainFrame frame){
		mainFrame=frame;
	}
        
}
