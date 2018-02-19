package kr.mubeat.cms;

import kr.mubeat.cms.util.AWSUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles(profiles = "local")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@WebAppConfiguration
//@EnableWebMvc
public class MubeatCmsServerApplicationTests {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//    AWSUtils awsUtils;

//	@Autowired
//	private ArtistRepository artistRepository;

//	@Test
	public void contextLoads() {

//		MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

//		awsUtils.uploadObjectToS3(firstFile, "/10000/test");
	}


//	@Test
	public void getSameAgencyArtist() {
//		List<ArtistDTO> artist = artistRepository.findSameAgencyArtist(1);
//		if(artist != null && artist.size() > 0) {
//			artist.get(0).getArtistName();
//		}
	}

//	@Test
	public void getDetail() {
//		ArtistDTO artistDTO = artistRepository.getArtistDetail(Long.parseLong("31"));
//		logger.debug(artistDTO.toString());
	}

}
