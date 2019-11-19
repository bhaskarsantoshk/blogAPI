package blogAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

    @RequestMapping("/")
    public String index(){
        return "Congratulations from BlogController.java";
    }

    //BlogMockedData blogMockedData = BlogMockedData.getInstance();
    @Autowired
    BlogRespository blogRespository;

    @GetMapping("/blog")
    public List<Blog> fetchBlogs(){
        //return blogMockedData.fetchBlogs();
        return blogRespository.findAll();
    }

    @GetMapping("/blog/{id}")
    public Blog showBlog(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        //return blogMockedData.getBlogById(blogId);
        return blogRespository.findOne(blogId);
    }

    @PostMapping("/blog/search")
    public List<Blog> search( @RequestBody Map<String, String> body){
        String searchTerm = body.get("text");
        //return blogMockedData.searchBlogs(searchTerm);
        return blogRespository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    @PostMapping("/blog")
    public Blog create( @RequestBody Map<String, String> body ){
        int id = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        //return blogMockedData.createBlog(id, title, content);
        return blogRespository.save(new Blog(title, content));
    }

    @PutMapping("/blog/{id}")
    public Blog update( @PathVariable String id, @RequestBody Map<String, String> body){
        int blogId = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        //return blogMockedData.updateBlog(blogId, title, content);
        Blog blog = blogRespository.findOne(blogId);
        blog.setContent(content);
        blog.setTitle(title);
        return blogRespository.save(blog);
    }

    @DeleteMapping("/blog/{id}")
    public boolean delete(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        //return blogMockedData.deleteBlog(blogId);
        blogRespository.delete(blogId);
        return true;
    }
}
