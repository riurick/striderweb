# striderweb
Strider Web Back-end Assessment - 2.1

#Post
- posts can be of type: POST, REPOST, QUOTEPOST. The ENUM TypeEnum will define that;
- for reposts and quote-posts, the associated post is related in the entity of the post itself;

#Databese
- The SpringBoot application is able to create and update a oracle database to this application;

#Tests
- There is some JUNIT tests for the REST calls

# Planning

Questions:

- Is the new feature "reply-to-post" a post itself?
- Will users use "@ mentioning" in this feature?
- Where should this new feed be displayed?

How to solve?

- a new option can be added in the toggle switch "All / following" to show the 'reply'
- a new ENUM can be defined in TypeEnum ('REPLYTOPOST') and we can continue using the associated post to link 'reply' with post

# Self-critique & scaling

- I would implement spring security with authentication, facilitating validations of what the user can or cannot do;
- Threads can be implemented to keep data up to date, like n. of followers.
- User registration can be done in another API, making the scalability independent for each API