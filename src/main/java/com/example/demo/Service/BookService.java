package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Books;
import com.example.demo.REpo.BooksREpo;
@Service
public class BookService {

	@Autowired
	BooksREpo booksREpo;
   public  void saveBooks(Books books) {
		
		booksREpo.save(books);
	}
	public List<Books> getAllBooks() {
		
		return booksREpo.findAll();
	}
	public Optional<Books> findById(long id) {
		// TODO Auto-generated method stub
		return booksREpo.findById(id);
	}
	public void deleteByid(long id) {
		booksREpo.deleteById(id);
		
	}
}
