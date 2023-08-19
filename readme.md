# Project Structure

docker-compose.yml file
```
version: '3.8'
services:
  cache:
    image: redis:6.2-alpine
    restart: always
    ports:
      - '6379:6379'
    command: redis-server --save 20 1 --loglevel warning --requirepass eYVX7EwVmmxKPCDmwMtyKVge8oLd2t81
    volumes:
      - cache:/data
volumes:
  cache:
    driver: local
```

dependecies:
```
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>
```
application.properties file:
```
redis.host=localhost
redis.port=6379
```

Redis config class:
```
@Configuration
@EnableCaching
public class RedisConfiguration {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }

}
```

service class functions:
```
    @Override
    @Cacheable(value = "book", key = "#bookId")
    public Book findBookById(long bookId) {
        longRunningMethod();
        return bookRepository
                .findById(bookId)
                .orElseThrow(
                        ()-> new RuntimeException("No book in repo")
                );
    }

    @Override
    @Cacheable("books")
    public List<Book> findBooks() {
        longRunningMethod();
        return bookRepository.findAll();
    }
```

additionally `@CachePut` and `@CacheEvict`:
```
@CachePut(value = "book", key = "#bookId")
public Book updateBook(long bookId, Book book) {
  //update codes
}

@CacheEvict(value = "book", key="#bookId")
public long deleteBook(long bookId) {
  //delete codes
}
```

## sql
- INSERT INTO book_db.books (author, is_published, name, year) VALUES ('Mustafa', false, 'Mustafa Book', 2001)

- book_db> INSERT INTO book_db.books (author, is_published, name, year) VALUES ('Test2', true, 'Test Book', 2002)

- book_db> INSERT INTO book_db.books (author, is_published, name, year) VALUES ('Test4', false, 'Test4', 2004)


link :
- https://medium.com/@mustafakaratas4179/redis-springboot-9d3ed193362d

