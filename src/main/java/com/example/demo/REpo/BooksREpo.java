package com.example.demo.REpo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Books;

@Repository
public interface BooksREpo extends JpaRepository<Books, Long>{

}
