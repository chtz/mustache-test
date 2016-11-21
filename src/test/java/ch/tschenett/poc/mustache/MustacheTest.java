package ch.tschenett.poc.mustache;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheTest {
	@Test
	public void test() throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
	    Mustache mustache = mf.compile(new InputStreamReader(getClass().getResourceAsStream("template")), "templateX");
	    
	    Context ctx = new Context();
		ctx.getUsers().add(new User("willi", "willi@foo.ch"));
		ctx.getUsers().add(new User("\"}}\n", "heiri@foo.ch"));
	    
	    Writer writer = new StringWriter();
	    mustache.execute(writer, ctx);
	    writer.close();
		
	    assertEquals("Name: willi, EMail: willi@foo.ch\nName: \"}}\n, EMail: heiri@foo.ch\n", writer.toString());
	}
	
	public static class Context {
		private final List<User> users = new LinkedList<User>();

		public List<User> getUsers() {
			return users;
		}
	}
	
	public static class User {
		private final String loginname;
		private final String email;
		
		public User(String loginname, String email) {
			this.loginname = loginname;
			this.email = email;
		}

		public String getLoginname() {
			return loginname;
		}

		public String getEmail() {
			return email;
		}
	}
}
