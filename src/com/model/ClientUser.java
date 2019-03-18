package com.model;

import com.basic.*;

public class ClientUser {

	public Message checkUser(User u)
	{
		return new ClientConServer().sendLoginInfoToServer(u);
	}
	
	public boolean unload(User u)
	{
		return new ClientConServer().sendunLoadInfoToServer(u);
	}
	public Message Registe(User u)
	{
		return new ClientConServer().sendRegisteInfoToServer(u);
	}
}
