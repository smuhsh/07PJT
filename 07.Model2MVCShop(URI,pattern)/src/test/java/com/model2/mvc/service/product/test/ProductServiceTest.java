package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  ProductServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
//==> Meta-Data 를 다양하게 Wiring 하자...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;

	//@Test // 2022-04-11 shhwang
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("will");
		product.setProdDetail("will");
		product.setManuDate("20220411");
		//product.setPrice(Integer.parseInt(("price")));
		product.setPrice(50000);
		product.setFileName("will.gif");
		
		productService.addProduct(product);
		
		//product = productService.getProduct("10000"); //왜 int 를 못 받는지 모르겠음 2022-04-09 알았음. ProductServiceImpl 자료형 보셈.

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals("will", product.getProdName());
		Assert.assertEquals("will", product.getProdDetail());
		Assert.assertEquals("20220411", product.getManuDate());
		Assert.assertEquals(50000, product.getPrice());
		Assert.assertEquals("will.gif", product.getFileName());
	}
	
	@Test // 2022-04-11 shhwang
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> 필요하다면...
//		user.setUserId("testUserId");
//		user.setUserName("testUserName");
//		user.setPassword("testPasswd");
//		user.setSsn("1111112222222");
//		user.setPhone("111-2222-3333");
//		user.setAddr("경기도");
//		user.setEmail("test@test.com");
		
		product = productService.getProduct(10021);//왜 int 를 못 받는지 모르겠음 2022-04-09 알았음. ProductServiceImpl 자료형 보셈.
		
		//==> console 확인
		System.out.println(product);
		
		//==> API 확인		
		Assert.assertEquals(10021, product.getProdNo());
		Assert.assertEquals("pixiv", product.getProdName());
		Assert.assertEquals("pixiv", product.getProdDetail());
		Assert.assertEquals("20220405", product.getManuDate());
		Assert.assertEquals(100000, product.getPrice());
		Assert.assertEquals("girl01.jpg", product.getFileName());
	}
	
	 //@Test // 2022-04-11 shhwang
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10026);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("shhwang", product.getProdName());
		Assert.assertEquals("shhwang", product.getProdDetail());
		Assert.assertEquals("20220409", product.getManuDate());
		Assert.assertEquals(50000, product.getPrice());
		Assert.assertEquals("demian.gif", product.getFileName());

		product.setProdName("데미안");
		product.setProdDetail("데미안");
		product.setManuDate("20220409");
		product.setPrice(10000);
		product.setFileName("demian.gif");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10026);
		Assert.assertNotNull(product);
		
		//==> console 확인
		System.out.println(product);
			
		//==> API 확인
		Assert.assertEquals("데미안", product.getProdName());
		Assert.assertEquals("데미안", product.getProdDetail());
		Assert.assertEquals("20220409", product.getManuDate());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("demian.gif", product.getFileName());
	 }
	 
	//@Test //이건 ProdNo에 이미 sequence를 주었기 때문에 할 필요가 없다.
//	public void testCheckDuplication() throws Exception{
//
//		//==> 필요하다면...
////		User user = new User();
////		user.setUserId("testUserId");
////		user.setUserName("testUserName");
////		user.setPassword("testPasswd");
////		user.setSsn("1111112222222");
////		user.setPhone("111-2222-3333");
////		user.setAddr("경기도");
////		user.setEmail("test@test.com");
////		
////		userService.addUser(user);
//		
//		//==> console 확인
//		System.out.println(productService.checkDuplication("testUserId"));
//		System.out.println(productService.checkDuplication("testUserId"+System.currentTimeMillis()) );
//	 	
//		//==> API 확인
//		Assert.assertFalse( userService.checkDuplication("testUserId") );
//	 	Assert.assertTrue( userService.checkDuplication("testUserId"+System.currentTimeMillis()) );
//		 	
//	}
	
	 //==>  주석을 풀고 실행하면....
	 //@Test // 2022-04-11 shhwang
	 public void testGetProductListAll() throws Exception{
		 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console 확인
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("");
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
		 	//==> console 확인
		 	//System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
	 
	 @Test // 2022-04-11 shhwang
	 public void testGetProductListByProductNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");// default 값이 0 이고 이것은 jsp 에서 setSearchCondition을 주어서 where 절 if문으로 쓰인다.
	 	search.setSearchKeyword("10026");//search를 ProductNo 으로 select 하겠다. 
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test // 2022-04-11 shhwang
	 public void testGetProductListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");//mapper에서 prod_no 으로 검색할 것인지 prod_name 으로 검색할 것인지...
	 	search.setSearchKeyword("스우");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());//setSearchKeyword 조건이 테이블에서 prod_name 의 개수만큼 일치한다면... 
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}