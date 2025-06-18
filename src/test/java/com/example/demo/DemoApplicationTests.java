package com.example.demo;

import com.example.demo.domain.BoardVO;
import com.example.demo.repository.BoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	void contextLoads() {
		for (int i = 0; i < 300; i++) {
			// builder 사용
			BoardVO boardVO = BoardVO.builder()
					.title("test title" + (int)(Math.random()*100 + 1))
					.writer("tester " + (int)(Math.random()*50 + 1))
					.content("test content " + i)
					.build();
			boardMapper.register(boardVO);
		}
	}

}
