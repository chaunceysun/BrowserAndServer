package server;

/**
 * HTTP：超文本传输协议  Hyper Text Transfer Protocol
 */
public abstract class HttpServlet {
    public abstract void service(HttpServletRequest request, HttpServletResponse response);
}
