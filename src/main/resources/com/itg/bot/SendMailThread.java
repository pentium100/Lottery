package com.itg.bot;

import java.util.concurrent.Callable;

import javax.mail.MessagingException;

public class SendMailThread implements Callable<Boolean> {

	private String smtp, from, to, copyto, subject, content, username,
			password;

	SendMailThread(String smtp, String from, String to, String copyto,
			String subject, String content, String username, String password) {

		this.smtp = smtp;
		this.from = from;
		this.to = to;
		this.copyto = copyto;
		this.subject = subject;
		this.content = content;
		this.username = username;
		this.password = password;

	}

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub

		SendMail theMail = new SendMail(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			throw new Exception("邮件发送－主题设置失败！");
		if (!theMail.setBody(content))
			throw new Exception("邮件发送－内容设置失败！");
		if (!theMail.setTo(to))
			throw new Exception("邮件发送－To设置失败！");
		if (!theMail.setCopyTo(copyto))
			throw new Exception("邮件发送－CopyTo设置失败！");
		if (!theMail.setFrom(from))
			throw new Exception("邮件发送－From设置失败！");
		theMail.setNamePass(username, password);
		int success = -1;

		while (success < 0 && success > -10) {
			try {
				theMail.sendOut();

				success = 1;

				
			} catch (MessagingException e) {
				success = success - 1;
				Thread.sleep(2*1000);

			}
		}

		return true;

	}
}
