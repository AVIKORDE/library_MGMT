package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.Books;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.User;
import com.example.demo.Service.BookService;
import com.example.demo.Service.tran;
import com.example.demo.Service.userservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class UserController {
	
	@Autowired
	userservice userService;
	@Autowired
	BookService bookService;
	@Autowired
	tran tran;
	
	@GetMapping("/")
	public String home(HttpServletRequest request,Model model) {
		String error=request.getParameter("message");
		if(error!=null)
		{
			 model.addAttribute("errorMessage", error);
		}
		return "index";
	}
	@GetMapping("/registrationts")
	public String registration() {

		return "userRegistration";
	}
	@PostMapping("/register")
	public String processRegistrationForm(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("mobileno") String mobileNo, Model model) {
		if (userService.existsByEmail(email)) {
	        model.addAttribute("emailError", "Email is already registered.");

	        return "registrationts"; 
	    }
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setMobile_no(mobileNo);
		user.setAdmin_id(0);
		userService.saveUser(user);
		return "redirect:/";

	}
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			Model model, HttpSession session) {
		User user = userService.findByemail(email);
		if (user != null && userService.verifyPassword(password, user.getPassword())) {
			session.setAttribute("loggedInUser", user);
			if(user.getAdmin_id()==0)
			{
				return "redirect:/orderList";
				
			}
			else
			{
				return "redirect:/user_list";
				
			}
			
		} else {
			
			model.addAttribute("error", "Invalid username or password");
			String error="Invalid Crediantial ";
			return "redirect:/?message=" + error;

		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.removeAttribute("loggedInUser");
		return "redirect:/";
	}
	@GetMapping("/user_list")
	public String listUsers(Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser != null) {

			List<User> userList = userService.getAllUsers();
			model.addAttribute("users", userList);
			return "user_list";
		} else {
			return "redirect:/";
		}
	}
	@GetMapping("/books_registration")
	public String depRegistration(HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser != null) {
			return "books_registration";
		} else {
			return "redirect:/";
		}
	}
	
	@PostMapping("/book_Register")
	public String depRegistrationbOOK(HttpSession session, 
	                                  @RequestParam("book_name") String book_name,
	                                  @RequestParam("qty") String qty,
	                                  @RequestParam("cost") String cost,
	                                  @RequestParam("author_name") String author_name, 
	                                  Model model) {
	    User loggedInUser = (User) session.getAttribute("loggedInUser");
	    if (loggedInUser != null) {
	        try {
	            Books book = new Books();
	            book.setBook_name(book_name);
	            book.setAuthor_name(author_name);
	            book.setCost(cost);
	            book.setQty(Long.parseLong(qty));

	            bookService.saveBooks(book);
	            model.addAttribute("message", "Book registered successfully!");
	            return "books_registration";
	        } catch (NumberFormatException e) {
	            model.addAttribute("error", "Invalid quantity format. Please enter a valid number.");
	            return "books_registration";
	        } catch (Exception e) {
	            model.addAttribute("error", "An error occurred while registering the book. Please try again.");
	            return "books_registration";
	        }
	    } else {
	        return "redirect:/";
	    }
	}
	@GetMapping("/books_list")
	public String booksList(Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser != null) {

			List<Books> bookslist = bookService.getAllBooks();
			model.addAttribute("bookslist", bookslist);
			return "bookslist";
		} else {
			return "redirect:/";
		}
	} 
	@GetMapping("/edit/{id}")
	public String Update(Model model, @PathVariable("id")long id, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser != null) {

			
			Optional<Books> book = bookService.findById(id);
			System.out.println(book.toString());
			model.addAttribute("user", book.get());
			return "edit";
		} else {
			return "redirect:/";
		}
	} //book_update
	@PostMapping("/book_update")
	public String bookUpdate(HttpSession session, 
	                         @RequestParam("book_name") String bookName,
	                         @RequestParam("qty") String qty,
	                         @RequestParam("cost") String cost,
	                         @RequestParam("author_name") String authorName,
	                         @RequestParam("id") long id,
	                         Model model) {
	    User loggedInUser = (User) session.getAttribute("loggedInUser");
	    if (loggedInUser != null) {
	        try {
	            // Convert quantity and cost to their respective types
	            long quantity = Long.parseLong(qty);
	            double costValue = Double.parseDouble(cost);
	            Books book = new Books();
	            book.setBook_name(bookName);
	            book.setAuthor_name(authorName);
	            book.setCost(cost);
	            book.setQty(quantity);
	            book.setId(id);
	            bookService.saveBooks(book);
	            return "redirect:/books_list";
	        } catch (Exception e) {
	            // Handle any other exceptions
	           
	            
	        }
	    } else {
	        return "redirect:/";
	    }
		return authorName;
	}
	@GetMapping("/delete/{id}")
	public String bookDelete(@PathVariable("id") long id,HttpSession session)
	{
		bookService.deleteByid(id);
		return "redirect:/books_list";
		
	}
	@GetMapping("/profile")
	public String profile(HttpSession session,Model model)
	{
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
	    if (loggedInUser != null) {
	    Optional<User> user=userService.findById(loggedInUser.getId());	
	    model.addAttribute("user", user.get());
		return "profile";
	    }
		
		return "redirect:/books_list";
		
	}
	@GetMapping("/viewById")
	public String edit_profile(HttpSession session,Model model)
	{
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
	    if (loggedInUser != null) {
	    Optional<User> user=userService.findById(loggedInUser.getId());	
	    model.addAttribute("user", user.get());
		return "editUser";
	    }
		
		return "redirect:/books_list";
		
	}
	@PostMapping("/userUpdate")
	public String userUpdate(HttpSession session, 
	                         @RequestParam("name") String name,
	                         @RequestParam("email") String email,
	                         @RequestParam("mobile_no") String mobile_no,
	                         
	                         Model model) {
	    User loggedInUser = (User) session.getAttribute("loggedInUser");
	    if (loggedInUser != null) {
	        try {
	            User user=new User();
	            user.setName(name);
	            user.setMobile_no(mobile_no);
	            user.setEmail(email);
	            user.setId(loggedInUser.getId());
	            userService.saveUser(user);
	            return "redirect:/profile";
	        } catch (Exception e) {
	        	e.printStackTrace();
	            }
	    } else {
	        return "redirect:/";
	    }
	    return "redirect:/profile";
	}
	@GetMapping("/orderList")
	public String OrderList(HttpSession session,Model model)
	{
			User loggedInUser = (User) session.getAttribute("loggedInUser");
	if (loggedInUser != null) {

		List<Books> bookslist = bookService.getAllBooks();
		model.addAttribute("bookslist", bookslist);
		return "orderList";
	} else {
		return "redirect:/";
	}
	}
	@GetMapping("/order/{id}")
	public String order(@PathVariable("id") long id, HttpSession session)
	{
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = currentDateTime.format(formatter);
        LocalDateTime newDateTime = currentDateTime.plusDays(5);
        String return_date=newDateTime.format(formatter);
        long userId=loggedInUser.getId();
        Transaction transaction=new Transaction();
        transaction.setB_id(id);
        transaction.setU_id(userId);
        transaction.setIssue_date(formattedDateTime);
        transaction.setReturn_date(return_date);
        tran.saveTRansaction(transaction);
        return "redirect:/orderList";
        
	}//

	@GetMapping("/order_list")
	public String orderList( HttpSession session,Model model)
	{
		List<Transaction> tan=tran.findAll();     
		model.addAttribute("tan", tan);
		return "order_list";
	}
}
