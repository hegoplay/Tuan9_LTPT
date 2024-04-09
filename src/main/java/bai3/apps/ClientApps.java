package bai3.apps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;

import bai3.entities.Address;
import bai3.entities.Comment;
import bai3.entities.News;
import bai3.entities.User;

public class ClientApps {
	private static Socket socket;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;

	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 9909);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			addData();
			
			out.writeObject(Option.GET_USER);
			out.writeInt(1);
			out.flush();
			User user;
			try {
				user = (User) in.readObject();
				System.out.println(user);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			out.writeObject(Option.GET_NEWS_BY_TAGS_OR_NEWS_CATEGORIES);
			out.writeObject("Fanta");
			out.flush();
			List<News> news;
			try {
				news = (List<News>) in.readObject();
				news.forEach(System.out::println);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			out.writeObject(Option.GET_STATISTIC_USER);
			out.flush();
			Map<User, Integer> map;
			try {
				 map = (Map<User, Integer>) in.readObject();
				 map.forEach((k,v)->{
					 System.out.println(k + " " + v);
                 });

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			out.writeObject(Option.EXIT);
			out.flush();
			in.close();
			out.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	private static void addData() {
		try {
			Address address =  new Address("HCMC", "TDC", "6 street", "713000");
			User u = new User(1,Set.of("abc","def"),"thmnh113@gmail.com","hegoplay","123456",address,Set.of());
			User u1 = new User(2,Set.of("abc","def"),"pmanhh19@gmail.com","phamTheManh","123456",address,Set.of());
			News n = new News(
					LocalDateTime.now(),
					1,
					Set.of("Horror","Fantasy"),
					"content",
					"DeepDarkFantasy",
					Set.of("tag1","tag2"),
					Set.of(1,2),
					Set.of());
			News n1 = new News(
					LocalDateTime.now(),
					2,
					Set.of("Mecha","Fantasy"),
					"content",
					"Robot",
					Set.of("imagination","ninja"),
					Set.of(5,2),
					Set.of());
			News n2 = new News(
					LocalDateTime.now(),
					3,
					Set.of("Another_world","idk","vanilla"),
					"Humannn",
					"I'm a human from another world",
					Set.of("human","normal"),
					Set.of(3,4),
					Set.of());
			Comment lsC[] = new Comment[] {
				new Comment("Good news",LocalDateTime.now(),u,n),
				new Comment("Bad news",LocalDateTime.now(),u,n1),
				new Comment("This one very kool",LocalDateTime.now(),u,n2),
			};
	
//			u.setComments(Stream.of(c).collect(Collectors.toSet()));
//			n.setComments(Set.of(c));
			
			pushData(Option.ADD_USER,u);
			boolean result = in.readBoolean();
			if (result) {
				System.out.println("Add user successfully");
			} else {
				System.out.println("Add user failed");
			}
			pushData(Option.ADD_USER,u1);
			result = in.readBoolean();
			if (result) {
				System.out.println("Add user successfully");
			} else {
				System.out.println("Add user failed");
			}
			
			pushData(Option.ADD_NEWS,n);
			pushData(Option.ADD_NEWS,n1);
			pushData(Option.ADD_NEWS,n2);
			for(Comment c:lsC) {
				pushData(Option.ADD_COMMENT,c);				
			}
			
		}
		catch(Exception e) {
            System.out.println(e.getMessage());
		}
	}
	private static void pushData(Option type,Object obj) throws IOException {
		out.writeObject(type);
		out.writeObject(obj);
		out.flush();
	}
}
