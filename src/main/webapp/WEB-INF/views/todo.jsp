<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todo List App</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
            min-height: 100vh;
            color: #e0e0e0;
            padding: 20px;
        }

        .container {
            max-width: 720px;
            margin: 0 auto;
            padding: 20px;
        }

        /* Header */
        .header {
            text-align: center;
            margin-bottom: 40px;
            animation: fadeInDown 0.6s ease-out;
        }

        .header h1 {
            font-size: 2.5rem;
            font-weight: 700;
            background: linear-gradient(135deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 8px;
        }

        .header p {
            color: #8888aa;
            font-size: 0.95rem;
        }

        /* Stats Bar */
        .stats-bar {
            display: flex;
            gap: 16px;
            margin-bottom: 24px;
            animation: fadeIn 0.8s ease-out;
        }

        .stat-card {
            flex: 1;
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.08);
            border-radius: 16px;
            padding: 16px;
            text-align: center;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .stat-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 24px rgba(102, 126, 234, 0.15);
        }

        .stat-number {
            font-size: 1.8rem;
            font-weight: 700;
            color: #667eea;
        }

        .stat-label {
            font-size: 0.75rem;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: #8888aa;
            margin-top: 4px;
        }

        /* Add Todo Form */
        .add-form {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.08);
            border-radius: 20px;
            padding: 24px;
            margin-bottom: 24px;
            animation: fadeIn 0.8s ease-out 0.2s both;
        }

        .form-row {
            display: flex;
            gap: 12px;
            margin-bottom: 12px;
        }

        .form-row:last-child {
            margin-bottom: 0;
        }

        input[type="text"], select {
            flex: 1;
            padding: 12px 16px;
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 12px;
            background: rgba(255, 255, 255, 0.05);
            color: #e0e0e0;
            font-family: 'Inter', sans-serif;
            font-size: 0.9rem;
            transition: border-color 0.3s, box-shadow 0.3s;
            outline: none;
        }

        input[type="text"]:focus, select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
        }

        input[type="text"]::placeholder {
            color: #666680;
        }

        select {
            cursor: pointer;
            max-width: 150px;
        }

        select option {
            background: #1a1a2e;
            color: #e0e0e0;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 12px;
            font-family: 'Inter', sans-serif;
            font-size: 0.9rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-1px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-danger {
            background: rgba(239, 68, 68, 0.15);
            color: #ef4444;
            border: 1px solid rgba(239, 68, 68, 0.2);
        }

        .btn-danger:hover {
            background: rgba(239, 68, 68, 0.25);
        }

        .btn-sm {
            padding: 6px 14px;
            font-size: 0.8rem;
            border-radius: 8px;
        }

        /* Filter Bar */
        .filter-bar {
            display: flex;
            gap: 8px;
            margin-bottom: 20px;
            animation: fadeIn 0.8s ease-out 0.3s both;
        }

        .filter-btn {
            padding: 8px 20px;
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 25px;
            background: transparent;
            color: #8888aa;
            font-family: 'Inter', sans-serif;
            font-size: 0.85rem;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
        }

        .filter-btn:hover, .filter-btn.active {
            background: rgba(102, 126, 234, 0.15);
            color: #667eea;
            border-color: rgba(102, 126, 234, 0.3);
        }

        /* Todo List */
        .todo-list {
            display: flex;
            flex-direction: column;
            gap: 12px;
            animation: fadeIn 0.8s ease-out 0.4s both;
        }

        .todo-item {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.08);
            border-radius: 16px;
            padding: 18px 20px;
            display: flex;
            align-items: center;
            gap: 16px;
            transition: all 0.3s;
            animation: slideIn 0.4s ease-out;
        }

        .todo-item:hover {
            border-color: rgba(102, 126, 234, 0.2);
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2);
            transform: translateX(4px);
        }

        .todo-item.completed {
            opacity: 0.6;
        }

        .todo-item.completed .todo-title {
            text-decoration: line-through;
            color: #666680;
        }

        .todo-checkbox {
            width: 22px;
            height: 22px;
            border-radius: 50%;
            border: 2px solid rgba(255, 255, 255, 0.2);
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
            transition: all 0.3s;
            background: transparent;
            color: transparent;
            font-size: 12px;
        }

        .todo-item.completed .todo-checkbox {
            background: linear-gradient(135deg, #667eea, #764ba2);
            border-color: #667eea;
            color: white;
        }

        .todo-content {
            flex: 1;
            min-width: 0;
        }

        .todo-title {
            font-weight: 600;
            font-size: 1rem;
            margin-bottom: 4px;
            color: #e0e0e0;
        }

        .todo-description {
            font-size: 0.85rem;
            color: #8888aa;
            line-height: 1.4;
        }

        .todo-meta {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-top: 6px;
        }

        .priority-badge {
            padding: 2px 10px;
            border-radius: 20px;
            font-size: 0.7rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .priority-HIGH {
            background: rgba(239, 68, 68, 0.15);
            color: #ef4444;
            border: 1px solid rgba(239, 68, 68, 0.2);
        }

        .priority-MEDIUM {
            background: rgba(245, 158, 11, 0.15);
            color: #f59e0b;
            border: 1px solid rgba(245, 158, 11, 0.2);
        }

        .priority-LOW {
            background: rgba(34, 197, 94, 0.15);
            color: #22c55e;
            border: 1px solid rgba(34, 197, 94, 0.2);
        }

        .todo-date {
            font-size: 0.7rem;
            color: #555577;
        }

        .todo-actions {
            display: flex;
            gap: 6px;
            flex-shrink: 0;
        }

        /* Empty State */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #555577;
            animation: fadeIn 0.8s ease-out;
        }

        .empty-state .emoji {
            font-size: 3rem;
            margin-bottom: 16px;
        }

        .empty-state p {
            font-size: 1rem;
            margin-bottom: 4px;
        }

        /* Footer */
        .footer {
            text-align: center;
            margin-top: 40px;
            padding: 20px;
            color: #555577;
            font-size: 0.8rem;
            animation: fadeIn 1s ease-out 0.6s both;
        }

        .footer .clear-btn {
            margin-top: 8px;
        }

        /* Animations */
        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes slideIn {
            from { opacity: 0; transform: translateX(-16px); }
            to { opacity: 1; transform: translateX(0); }
        }

        /* Responsive */
        @media (max-width: 600px) {
            .header h1 { font-size: 1.8rem; }
            .stats-bar { flex-direction: column; gap: 8px; }
            .form-row { flex-direction: column; }
            select { max-width: 100%; }
            .todo-item { flex-wrap: wrap; }
            .todo-actions { width: 100%; justify-content: flex-end; }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1>&#10003; Todo List</h1>
            <p>Stay organized, stay productive</p>
        </div>

        <!-- Stats -->
        <div class="stats-bar">
            <div class="stat-card">
                <div class="stat-number">${totalCount}</div>
                <div class="stat-label">Total</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${completedCount}</div>
                <div class="stat-label">Completed</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${pendingCount}</div>
                <div class="stat-label">Pending</div>
            </div>
        </div>

        <!-- Add Form -->
        <div class="add-form">
            <form action="${pageContext.request.contextPath}/todos" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-row">
                    <input type="text" name="title" placeholder="What needs to be done?" required>
                    <select name="priority">
                        <option value="LOW">Low</option>
                        <option value="MEDIUM" selected>Medium</option>
                        <option value="HIGH">High</option>
                    </select>
                </div>
                <div class="form-row">
                    <input type="text" name="description" placeholder="Add a description (optional)">
                    <button type="submit" class="btn btn-primary">+ Add</button>
                </div>
            </form>
        </div>

        <!-- Filter Bar -->
        <div class="filter-bar">
            <a href="${pageContext.request.contextPath}/todos" 
               class="filter-btn ${currentFilter == 'all' ? 'active' : ''}">All</a>
            <a href="${pageContext.request.contextPath}/todos?filter=pending" 
               class="filter-btn ${currentFilter == 'pending' ? 'active' : ''}">Pending</a>
            <a href="${pageContext.request.contextPath}/todos?filter=completed" 
               class="filter-btn ${currentFilter == 'completed' ? 'active' : ''}">Completed</a>
        </div>

        <!-- Todo List -->
        <div class="todo-list">
            <c:choose>
                <c:when test="${empty todos}">
                    <div class="empty-state">
                        <div class="emoji">&#128203;</div>
                        <p>No todos yet!</p>
                        <p style="font-size: 0.85rem;">Add your first task above to get started.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="todo" items="${todos}">
                        <div class="todo-item ${todo.completed ? 'completed' : ''}">
                            <!-- Toggle -->
                            <form action="${pageContext.request.contextPath}/todos" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="toggle">
                                <input type="hidden" name="id" value="${todo.id}">
                                <button type="submit" class="todo-checkbox" title="Toggle complete">
                                    ${todo.completed ? '&#10003;' : ''}
                                </button>
                            </form>

                            <!-- Content -->
                            <div class="todo-content">
                                <div class="todo-title">${todo.title}</div>
                                <c:if test="${not empty todo.description}">
                                    <div class="todo-description">${todo.description}</div>
                                </c:if>
                                <div class="todo-meta">
                                    <span class="priority-badge priority-${todo.priority}">${todo.priority}</span>
                                    <span class="todo-date">${todo.createdAt}</span>
                                </div>
                            </div>

                            <!-- Delete -->
                            <div class="todo-actions">
                                <form action="${pageContext.request.contextPath}/todos" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${todo.id}">
                                    <button type="submit" class="btn btn-danger btn-sm" title="Delete">&#10005;</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Footer -->
        <div class="footer">
            <p>Todo List App &mdash; Built with Java Servlets</p>
            <c:if test="${completedCount > 0}">
                <form action="${pageContext.request.contextPath}/todos" method="post" style="margin-top: 8px;">
                    <input type="hidden" name="action" value="clearCompleted">
                    <button type="submit" class="btn btn-danger btn-sm">Clear ${completedCount} completed</button>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>
