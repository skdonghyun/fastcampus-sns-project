package com.fastcampus.snsproject.contoller;

import com.fastcampus.snsproject.contoller.request.PostCreateRequest;
import com.fastcampus.snsproject.contoller.request.PostModifyRequest;
import com.fastcampus.snsproject.contoller.response.PostResponse;
import com.fastcampus.snsproject.contoller.response.Response;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {

        postService.create(request.getTitle(), request.getBody(), authentication.getName());

        return Response.success();
    }


    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Integer postId, @RequestBody PostModifyRequest request, Authentication authentication) {

        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);

        return Response.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Integer postId, Authentication authentication) {

        postService.delete(authentication.getName(), postId);
        return Response.success();
    }


    @GetMapping
    public Response<Page<PostResponse>> list(Pageable pageable, Authentication authentication){

        return Response.success(postService.list(pageable).map(PostResponse::fromPost));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> my( Pageable pageable, Authentication authentication){
        return Response.success(postService.my(authentication.getName(), pageable).map(PostResponse::fromPost));
    }


}
