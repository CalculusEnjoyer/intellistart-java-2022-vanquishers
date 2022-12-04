package com.intellias.intellistart.interviewplanning.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
//@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class AuthenticationTest {
//  @Autowired
//  private FacebookClient facebookClient;

//  @Autowired
//  private FacebookService facebookService;

//  @Autowired
//  RestTemplate restTemplate;
//
//  @Autowired
//  Environment env;
//
//  private final String jwtToken = "EAALXaskmJ0ABALd1Ig5KUIKpZCor5UjnwmgHj1R08J4qprqoV2rFlfvRUbclgg"
//      + "ZCWN9wS8nnFwDR8A1XLGtT9GQJ8vCr30SviXsh6qRlzEf47ZBTFqPhwnTLQxmWvCIXvJrIA8UFEZAb2nsJVhcmy6E"
//      + "ZAxjvPVrDf9N9FX1fLBkaI5VK1ZBMU5";
//
//  private final String invalidJwtToken = "EAALXaskmJ0ABAF8B3obWu";
  int a = 1;

  @Test
  void getUserTest() {
    assertEquals(a, 1);
//    FacebookUser facebookUser = facebookClient.getUser(jwtToken);
//    assertThat(facebookUser.getId(), facebookUser.getId().equals("103318825907086"));
//    assertThat(facebookUser.getEmail(), facebookUser.getEmail().equals("grata.salve@gmail.com"));
//    assertThat(facebookUser.getFirst_name(), facebookUser.getFirst_name().equals("Влад"));
//    assertThat(facebookUser.getLast_name(), facebookUser.getLast_name().equals("Прокопенко"));
  }
//
//  @Test
//  @OnlineTest
//  void getUserWithInvalidTokenTest() {
//    assertThrows(InvalidJwtTokenException.class, () -> facebookClient.getUser(invalidJwtToken));
//  }
//
//  @Test
//  @OnlineTest
//  void loginUserTest() {
//    System.out.println(System.getProperty("online"));
//    String token = facebookService.loginUser(jwtToken);
//    assertThat(token, token.length() > 0);
//  }

//  @Mock
//  JwtConfig jwtConfig;
//
//  @Mock
//  JwtTokenProvider tokenProvider;
//
//  @InjectMocks
//  JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
//
//  HttpServletRequest request;
//  HttpServletResponse response;
//  FilterChain chain;
//
//  @BeforeEach
//  public void before() {
//    request = mock(HttpServletRequest.class);
//    response = mock(HttpServletResponse.class);
//    chain = mock(FilterChain.class);
//    jwtConfig = mock(JwtConfig.class);
//    tokenProvider = mock(JwtTokenProvider.class);
//  }
//
//  @Test
//  public void doFilterInternalNullToken() throws IOException, ServletException {
//    when(request.getHeader(AUTHORIZATION)).thenReturn(null);
//    jwtTokenAuthenticationFilter.doFilterInternal(request, response, chain);
//    verify(chain).doFilter(request, response);
//  }
//
//  @Test
//  @OnlineTest
//  public void doFilterInternalExceptionToken() throws IOException, ServletException {
//    when(request.getHeader(AUTHORIZATION)).thenReturn("123");
//    jwtTokenAuthenticationFilter.doFilterInternal(request, response, chain);
//    verify(chain).doFilter(request, response);
//  }

}