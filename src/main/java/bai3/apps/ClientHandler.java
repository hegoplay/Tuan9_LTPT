package bai3.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import all.Constant;
import bai3.entities.Comment;
import bai3.entities.News;
import bai3.entities.User;
import bai3.utils.CommentDAO;
import bai3.utils.ConnectDB;
import bai3.utils.NewsDAO;
import bai3.utils.UserDAO;

public class ClientHandler implements Runnable {
	
	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			do {
				Option o = (Option) in.readObject();
//				Write an enum to handle the options
				if (o == null || o == Option.EXIT) {
					break;
				}
				if(o == Option.ADD_USER) {
					User u = (User) in.readObject();
					UserDAO dao = new UserDAO();
					boolean result = dao.addUser(u);
					out.writeBoolean(result);
					out.flush();
				}
				if(o==Option.ADD_NEWS) {
					News n = (News) in.readObject();
					NewsDAO dao = new NewsDAO();
					dao.insert(n);
				}
				if(o==Option.ADD_COMMENT) {
					Comment c = (Comment) in.readObject();
					CommentDAO dao = new CommentDAO();
					dao.insert(c);
				}
				if (o == Option.GET_NEWS) {
					int id = in.readInt();
					NewsDAO dao = new NewsDAO();
					News n = dao.findById(id);
					out.writeObject(n);
					out.flush();
				}
				if (o == Option.GET_USER) {
					int id = in.readInt();
					UserDAO dao = new UserDAO();
					User u = dao.findById(id);
					out.writeObject(u);
					out.flush();
				}
				if (o == Option.GET_ALL_NEWS) {
					NewsDAO dao = new NewsDAO();
					List<News> ls = dao.findAllNews();
					out.writeObject(ls);
					out.flush();
				}
				if(o==Option.GET_NEWS_BY_TAGS_OR_NEWS_CATEGORIES) {
					String tags = (String) in.readObject();
					NewsDAO dao = new NewsDAO();
					List<News> ls = dao.getNewsByTagsOrNewsCategories(tags);
					out.writeObject(ls);
					out.flush();
				}
				if (o == Option.GET_STATISTIC_USER) {
					UserDAO dao = new UserDAO();
					Map<User, Integer> ls = dao.getStatistic();
					out.writeObject(ls);
					out.flush();
				}
			}
			while (true);
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
