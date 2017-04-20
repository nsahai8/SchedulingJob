# Scheduler Web Service
The Project is about invoking the URL at scheduled time.It is scheduled asynchronously at specified time and finish it.
1. Client can schedule their task to invoke it at given period of time.
2. Client should be able to check status of their tasks (completed or not).
3. Client should be able to update the tasks scheduled before it is processed.
4. System must be able to handle multiple requests at the same time.
5. The Urls are hit using httpclient at the specified time and response is saved in DB.

The Project uses Posgres DB to store user and URL and scheduled time.And after hiting the URL, it saves the response.

APIS:
1.POST /tasks        -    schedule new task
2.GET /tasks/:id     -    get job by id
3.PUT /tasks/:id     -    update tasks by id
4.DELETE /tasks/:id  -    delete tasks by id
