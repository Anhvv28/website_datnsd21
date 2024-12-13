import React, { useState } from 'react';
import axios from 'axios';
import { AiOutlineClose, AiOutlineSend, AiOutlineMessage } from 'react-icons/ai';
import './chatbot.css';

const Chatbot: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState<{ user: boolean; text: string; timestamp: string }[]>([]);
  const [input, setInput] = useState('');
  const [isTyping, setIsTyping] = useState(false);

  const toggleChatbot = () => {
    setIsOpen(!isOpen);
  };

  const handleSend = async () => {
    if (input.trim()) {
      const newMessage = { user: true, text: input, timestamp: new Date().toLocaleTimeString() };
      setMessages((prevMessages) => [...prevMessages, newMessage]); // Thêm tin nhắn người dùng vào state

      setInput(''); // Xóa nội dung input

      try {
        setIsTyping(true); // Hiển thị trạng thái typing của chatbot

        // Gửi yêu cầu đến API
        const response = await axios.post('http://localhost:8080/api/chat', {
          userMessage: input,
        });

        // Nhận phản hồi từ API
        const botResponse = response.data;
        const botMessage = { user: false, text: botResponse, timestamp: new Date().toLocaleTimeString() };

        // Thêm phản hồi của bot vào state
        setMessages((prevMessages) => [...prevMessages, botMessage]);
      } catch (error) {
        console.error('Error connecting to the chatbot API:', error);
        const errorMessage = { user: false, text: 'Xin lỗi, không thể kết nối tới chatbot vào lúc này.', timestamp: new Date().toLocaleTimeString() };
        setMessages((prevMessages) => [...prevMessages, errorMessage]);
      } finally {
        setIsTyping(false); // Ẩn trạng thái typing sau khi phản hồi hoặc lỗi
      }
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInput(e.target.value);
  };

  return (
    <div className="chatbot-container">
      <button className="chatbot-toggle" onClick={toggleChatbot}>
        <AiOutlineMessage size={24} />
        {isOpen ? 'Close Chat' : 'Chat with us!'}
      </button>

      {isOpen && (
        <div className="chatbot-box">
          <div className="chatbot-header">
            <h3>Chatbot</h3>
            <button className="close-button" onClick={toggleChatbot}>
              <AiOutlineClose size={20} />
            </button>
          </div>
          <div className="chatbot-content">
            <div className="chatbot-messages">
              {messages.map((msg, index) => (
                <div
                  key={index}
                  className={`chatbot-message ${msg.user ? 'user' : 'bot'}`}
                >
                  <div className="message-text">{msg.text}</div>
                  <div className="message-timestamp">{msg.timestamp}</div>
                </div>
              ))}
              {isTyping && (
                <div className="chatbot-message bot">
                  <div className="message-text">Chatbot is typing...</div>
                </div>
              )}
            </div>
          </div>
          <div className="chatbot-input">
            <input
              type="text"
              value={input}
              onChange={handleInputChange}
              placeholder="Type a message..."
              onKeyDown={(e) => e.key === 'Enter' && handleSend()}
            />
            <button className="send-button" onClick={handleSend}>
              <AiOutlineSend size={20} />
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Chatbot;
