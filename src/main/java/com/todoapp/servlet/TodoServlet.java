package com.todoapp.servlet;

import com.todoapp.model.Todo;
import com.todoapp.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet controller for handling Todo operations.
 */
@WebServlet(urlPatterns = {"/todos", "/todos/*"})
public class TodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoService todoService;

    @Override
    public void init() throws ServletException {
        // Use application-scoped TodoService for shared state
        todoService = (TodoService) getServletContext().getAttribute("todoService");
        if (todoService == null) {
            todoService = new TodoService();
            getServletContext().setAttribute("todoService", todoService);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filter = request.getParameter("filter");
        List<Todo> todos;

        if ("completed".equalsIgnoreCase(filter)) {
            todos = todoService.getTodosByStatus(true);
        } else if ("pending".equalsIgnoreCase(filter)) {
            todos = todoService.getTodosByStatus(false);
        } else {
            todos = todoService.getAllTodos();
        }

        request.setAttribute("todos", todos);
        request.setAttribute("totalCount", todoService.getTodoCount());
        request.setAttribute("completedCount", todoService.getCompletedCount());
        request.setAttribute("pendingCount", todoService.getPendingCount());
        request.setAttribute("currentFilter", filter != null ? filter : "all");

        request.getRequestDispatcher("/WEB-INF/views/todo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "add";
        }

        switch (action) {
            case "add":
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String priority = request.getParameter("priority");
                if (title != null && !title.trim().isEmpty()) {
                    todoService.addTodo(title, description, priority);
                }
                break;

            case "toggle":
                int toggleId = Integer.parseInt(request.getParameter("id"));
                todoService.toggleTodo(toggleId);
                break;

            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                todoService.deleteTodo(deleteId);
                break;

            case "update":
                int updateId = Integer.parseInt(request.getParameter("id"));
                String updateTitle = request.getParameter("title");
                String updateDesc = request.getParameter("description");
                String updatePriority = request.getParameter("priority");
                todoService.updateTodo(updateId, updateTitle, updateDesc, updatePriority);
                break;

            case "clearCompleted":
                todoService.clearCompleted();
                break;

            default:
                break;
        }

        response.sendRedirect(request.getContextPath() + "/todos");
    }
}
