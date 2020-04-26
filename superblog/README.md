# Spring-Data-Mongo-Assignment
## Requirements
Assume that we're building an API set for a blogging platform. The blogging platform must support below features:

* Super admin can write articles and categorize articles under different categories and labeling tags for them
* Super admin can delete / update articles
* End user can post comment under each article
* Super user can manage comments: approve, delete it

## Expectation on the output
* All related model entities must be defined with good relationship
* All related APIs to satisfy above requirement must be implemented
* All related unit tests must be implemented

## Solution
1. Database models of article, comment and user.
   - User model (see [User.java](src/main/java/com/homework/superblog/model/User.java))
     - `{`<br/>
         `__"id" : [id of the user - type: ObjectId],`<br/>
         `__"name" : [name of the user - type: String],`<br/>
         `__"email" : [email of the user - type: String],`<br/>
         `__"address" : [address of the user - type: Address ]`(see [Address.java](src/main/java/com/homework/superblog/model/Address.java))<br/>
         `__{`<br/>
         `_____"streetAddress" : [type: String],`<br/>
         `_____"city" : [type: String],`<br/> 
         `_____"country" : [type: String]`<br/>
         `__}`<br/>
         `__"authorized" : [roles and permossions granted for user - type: Authorized]`(see [Authorized.java](src/main/java/com/homework/superblog/model/Authorized.java))<br/>
         `__{`<br/>
         `____"roles" : [type: EnumSet<Role> - Role : {END_USER, SUPER_USER, SUPER_ADMIN}],`(see [Role.java](src/main/java/com/homework/superblog/model/Role.java))<br/>
         `____"permissions" : [type: EnumSet<Permission> - Permission: {CREATE_ARTICLE, UPDATE_ARTICLE,`<br/>
         `____SET_CATEGORIES_ARTICLE, SET_TAGS_ARTICLE, DELETE_ARTICLE,`<br/>
         `____POST_COMMENT, APPROVE_COMMENT, DELETE_COMMENT}]`(see [Permission.java](src/main/java/com/homework/superblog/model/Permission.java))<br/>
         `__},`<br/>
         `__"enabled" : [tells if the user is banned (false) or not (true) - type: boolean]`<br/>
       `}`
       
    - Article model (see [Article.java](src/main/java/com/homework/superblog/model/Article.java))
      - `{`<br/>
        `__"id" : [id of the article - type: ObjectId],`<br/>
        `__"title" : [name of the article - type: String],`<br/>
        `__"content" : [content of the article - type: String],`<br/>
        `__"authorId" : [id of the author - type: ObjectId],`<br/>
        `__"categories : [categories of the article - type: EnumSet<Category],`(see [Category.java](src/main/java/com/homework/superblog/model/Category.java))<br/>
        `__"tags" : [tags of the article - type: List<String>]`<br/>
        `}`
        
    - Comment model (see [Comment.java](src/main/java/com/homework/superblog/model/Comment.java))
      - `{`<br/>
        `__"id" : [id of the comment - type: ObjectId],`<br/>
        `__"email" : [email of the comment - type: String],`<br/>
        `__"content" : [content of the article - type: String],`<br/>
        `__"articleId" : [id of the article - type: ObjectId],`<br/>
        `__"approved" : [tells if the article is approved - type: boolean]`<br/>
        `}`
 2. Provide HTTP GET/POST/PUT/DELETE requests
    - UserApi (see [UserApi.java](src/main/java/com/homework/superblog/api/UserApi.java))
      - Get all users
        - `GET https://localhost:9090/superblog/users`
      - Get users by email
        - `GET https://localhost:9090/superblog/users/{email}`
      - Get users by name starts with
        - `GET https://localhost:9090/superblog/users?name-start-with={String(case sensitive)}`
      - Create user
        - `POST https://localhost:9090/superblog/users + {JSON body with all the fields of type User}`
      - Ban user by email.
        - `PUT https://localhost:9090/superblog/users/ban?email={String}`
      - Re-authorize a user
        - `PUT https://localhost:9090/superblog/users/{id}/authorized {JSON body with all the fields of type Authorized}`
      - Delete a user by email
        - `DELETE https://localhost:9090/users/{email}`
    - ArticleApi (see [ArticleApi.java](src/main/java/com/homework/superblog/api/ArticleApi.java))
      - Get article with pagination
        - `GET https://localhost:9090/superblog/articles?size={int}&page={int}`
      - Get articles by title keyword (case insensitive)
        - `GET https://localhost:9090/superblog/articles/title?key={String}`
      - Create article
        - `POST https://localhost:9090/superblog/articles + {JSON body with all the fields of type Article}`
      - Update article
        - `PUT https://localhost:9090/superblog/articles + {JSON body with all the fields of type Article}`
      - Update categories
        - `PUT https://localhost:9090/superblog/articles/{id}/categories + {JSON body with all the fields of type EnumSet<Category>}`
      - Update tags
        - `PUT https://localhost:9090/superblog/articles/{id}/tags + {JSON body with all the fields of type List<String>}`
    - CommentApi (see [CommentApi.java](src/main/java/com/homework/superblog/api/CommentApi.java))
      - Create comment
        - `POST https://localhost:9090/superblog/comments + {JSON body with all the fields of type Comment}`
      - Get comment by article id
        - `GET https://localhost:9090/superblog/comments?articleId={articleId}`
      - Get comments by article id and approved with pagination
        - `GET https://localhost:9090/superblog/comments?articleId={ObjectId}&approved={boolean}&size={int}&page={int}`
      - Get comments by article id and approved
        - `GET https://localhost:9090/superblog/comments?articleId={ObjectId}&approved={boolean}`
      - Approve comment
        - `PUT https://localhost:9090/superblog/comments/approve?id={ObjectId}`
      - Delete comment
        - `DELETE https://localhost:9090/superblog/comments/{id}`
3. Unit tests
   - [x] [Database test.](src/test/java/com/homework/superblog/repository/DatabaseTest.java)
   - [x] [ApplicationTests.](src/test/java/com/homework/superblog/SuperblogApplicationTests.java)
   - [x] [Service tests. All the functions in service are tested.](src/test/java/com/homework/superblog/service)
4. Define profiles e.g dev & qc
   - [__develoment__ environment properties](src/main/resouces/application-dev.properties)
   - [__test__ environment properties](src/main/resouces/application-test.properties)
