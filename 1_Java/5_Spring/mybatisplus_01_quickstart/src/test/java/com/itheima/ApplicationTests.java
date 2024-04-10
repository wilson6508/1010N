package com.itheima;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserDao userDao;

	@Test
	void testInsert() {
		User user = new User();
		user.setName("Tom");
		user.setPassword("5566");
		user.setAge(21);
		user.setTel("12345678");
		userDao.insert(user);
	}

	@Test
	void testUpdate() {
		User user = new User();
		user.setId(1L);
		user.setName("Eng1234");
		userDao.updateById(user);
	}

	@Test
	void testDelete() {
		userDao.deleteById(1674072466123755521L);
	}

	@Test
	void testSelectAll() {
		List<User> users = userDao.selectList(null);
		System.out.println(users);
	}

	@Test
	void testGetPage() {
		IPage page = new Page(3,2);
		userDao.selectPage(page, null);
		System.out.println("當前頁碼 = " + page.getCurrent());
		System.out.println("每頁顯示幾條 = " + page.getSize());
		System.out.println("一共幾頁 = " + page.getPages());
		System.out.println("一共多少條數據 = " + page.getTotal());
		System.out.println("數據 = " + page.getRecords());
	}

}
