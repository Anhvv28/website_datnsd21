// src/pages/chatbot/chatbot.tsx
import React, { useState } from 'react';
import './chatbot.css';

const Chatbot: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState<{ user: boolean; text: string }[]>([]);
  const [input, setInput] = useState('');

  const toggleChatbot = () => {
    setIsOpen(!isOpen);
  };

  const handleSend = () => {
    if (input.trim()) {
      // Add user message to the chat
      setMessages([...messages, { user: true, text: input }]);
      
      // Optionally, here you can call a function to get chatbot responses, e.g., from an API
      // Simulating a chatbot response for now
      setMessages((prevMessages) => [
        ...prevMessages,
        { user: true, text: input },
        { user: false, text: "This is a response from the chatbot." },
      ]);

      setInput(''); // Clear the input field
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInput(e.target.value);
  };

  return (
    <div className="chatbot-container">
      <button className="chatbot-toggle" onClick={toggleChatbot}>
        {isOpen ? 'Close Chat' : 'Chat with us!'}
      </button>

      {isOpen && (
        <div className="chatbot-box">
          <div className="chatbot-header">
            <h3>Chatbot</h3>
            <button onClick={toggleChatbot}>X</button>
          </div>
          <div className="chatbot-content">
            <div className="chatbot-messages">
              {messages.map((msg, index) => (
                <div
                  key={index}
                  className={`chatbot-message ${msg.user ? 'user' : 'bot'}`}
                >
                  {msg.text}
                </div>
              ))}
            </div>
          </div>
          <div className="chatbot-input">
            <input
              type="text"
              value={input}
              onChange={handleInputChange}
              placeholder="Type a message..."
            />
            <button onClick={handleSend}>Send</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Chatbot;
