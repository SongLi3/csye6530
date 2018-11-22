//package org.songli.iot.module5;
//
//import java.util.Properties;
//import Javax.mail.Session;
//
//public class TempManagementApp {
//	public class StoreMail {
//		 final static String USER = "songlizzza"; // 用户名
//		 final static String PASSWORD = "123456a"; // 密码
//		 public final static String MAIL_SERVER_HOST = "pop.163.com"; // 邮箱服务器
//		 public final static String TYPE_HTML = "text/html;charset=UTF-8"; // 文本内容类型
//		 public final static String MAIL_FROM = "[email protected]"; // 发件人
//		 public final static String MAIL_TO = "[email protected]"; // 收件人
//		 public final static String MAIL_CC = "[email protected]"; // 抄送人
//		 public final static String MAIL_BCC = "[email protected]"; // 密送人
//		 public void main(String[] args) throws Exception {
//		  // 创建一个有具体连接信息的Properties对象
//		  Properties prop = new Properties();
//		  prop.setProperty("mail.debug", "true");
//		  prop.setProperty("mail.store.protocol", "pop3");
//		  prop.setProperty("mail.pop3.host", MAIL_SERVER_HOST);
//		  // 1、创建session
//		  Session session = Session.getInstance(prop);
//		  // 2、通过session得到Store对象
//		  Store store = session.getStore();
//		  // 3、连上邮件服务器
//		  store.connect(MAIL_SERVER_HOST, USER, PASSWORD);
//		  // 4、获得邮箱内的邮件夹
//		  Folder folder = store.getFolder("inbox");
//		  folder.open(Folder.READ_ONLY);
//		  // 获得邮件夹Folder内的所有邮件Message对象
//		  Message[] messages = folder.getMessages();
//		  for (int i = 0; i < messages.length; i++) {
//		   String subject = messages[i].getSubject();
//		   String from = (messages[i].getFrom()[0]).toString();
//		   System.out.println("第 " + (i + 1) + "封邮件的主题：" + subject);
//		   System.out.println("第 " + (i + 1) + "封邮件的发件人地址：" + from);
//		  }
//		  // 5、关闭
//		  folder.close(false);
//		  store.close();
//		 }
//		}
//}
