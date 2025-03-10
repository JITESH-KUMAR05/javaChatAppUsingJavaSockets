# Java Chat Application

A robust real-time chat application built using Java Swing and Socket Programming that demonstrates core networking principles and graphical user interface design.

## Project Overview
This application implements a client-server architecture for real-time messaging between users. It showcases fundamental concepts of network programming, multithreading, and responsive UI design in Java.

## Ownership & Disclaimer
This project is created and owned by Jitesh Kumar.
This is an educational project and is intended for learning purposes only.
Please do not use this code in your projects without proper permission.

© 2024 Jitesh Kumar. All Rights Reserved.

## Features

- Real-time bidirectional messaging between users
- Modern UI with customized profile pictures and status indicators
- Message timestamps for conversation tracking
- Color-coded message bubbles (sent vs received)
- Active status indicators for connection awareness
- Minimalist and intuitive design
- Non-blocking message handling via multithreading
- Dynamic message sizing based on content

## System Requirements

- Java SE 22 or higher
- Minimum 4GB RAM
- 100MB free disk space
- Network connectivity for multi-device operation
- Display resolution of 1280x720 or higher (recommended)

## Technical Architecture

### Technology Stack
- Java SE 22
- Java Swing for GUI components
- Socket Programming for network communication
- Multi-threading for asynchronous operations
- Object-oriented design patterns

### Communication Flow
1. Server initializes and listens on port 6001
2. Client connects to server via socket
3. Bidirectional data streams established
4. Messages transmitted as text streams
5. Dedicated threads handle I/O operations

## Project Structure 
```
chatapp/
├── src/
│   ├── Server.java      # Server implementation with socket listener
│   └── Client.java      # Client implementation with connection logic
└── icons/               # UI assets
    ├── 1.png            # Server profile picture
    ├── 2.png            # Client profile picture
    ├── 3.png            # Back button icon
    ├── 3icon.png        # More options icon
    ├── video.png        # Video call icon
    └── phone.png        # Phone call icon
```

## Detailed Component Explanation

### Server Component
- Initializes `ServerSocket` on port 6001
- Creates and manages socket connection
- Implements multithreaded message handling
- Renders UI with custom header and message panel
- Processes incoming messages and updates UI atomically

### Client Component
- Establishes connection to server via `Socket`
- Mirrors server functionality with unique styling
- Handles connection failures gracefully
- Maintains persistent connection with error handling
- Updates UI based on message direction (sent/received)

## UI Design Elements

- **Header Panel**: Contains profile information, status, and action buttons
- **Message Area**: Scrollable panel displaying conversation history
- **Input Area**: Text field for message composition with send button
- **Message Bubbles**: Custom JPanel implementations with rounded corners
- **Status Indicators**: Dynamic indicators showing connection state

## Implementation Details

### GUI Implementation
```java
// Sample code showing message panel creation
JPanel messagePanel = new JPanel();
messagePanel.setLayout(new BorderLayout());
messagePanel.setBackground(isOutgoing ? new Color(233, 76, 161) : Color.WHITE);
messagePanel.setBorder(new EmptyBorder(15, 15, 15, 50));
```

### Network Communication
```java
// Sample code showing message reception
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            while (true) {
                String message = dis.readUTF();
                displayMessage(message, false);
            }
        } catch (Exception e) {
            System.out.println("Connection closed");
        }
    }
}).start();
```

### Threading Model
- Main UI thread for interface operations
- Dedicated read thread for incoming messages
- Write operations performed on demand
- Swing's `invokeLater` used for thread-safe UI updates

## How to Run

1. Compile both Java files:
```bash
javac Server.java
javac Client.java
```

2. Start the Server first:
```bash
java Server
```

3. Start the Client:
```bash
java Client
```

## Testing Procedures

### Functionality Testing
1. Start server and verify listener activation
2. Connect client and verify connection establishment
3. Send messages from both sides to verify bidirectional communication
4. Test long messages for proper display formatting
5. Test rapid message exchange for race conditions

### Performance Testing
1. Measure message delivery latency
2. Test with varying message sizes
3. Evaluate UI responsiveness during active messaging
4. Monitor memory usage during extended sessions

## Challenges and Solutions

### Challenge: Message Synchronization
**Solution**: Implemented dedicated reader threads and used Swing's `invokeLater` for thread-safe UI updates

### Challenge: Dynamic Message Sizing
**Solution**: Created custom layout manager that adapts to message content length

### Challenge: Connection Management
**Solution**: Added error handling for socket exceptions and connection termination

## Known Limitations
- Supports only two users concurrently
- Operates within localhost environment only
- No message persistence between sessions
- No encryption for message security
- Limited error recovery for network failures

## Future Improvements
- Multi-user chat room functionality
- Message database for history retention
- File and media sharing capabilities
- End-to-end encryption for message security
- Emoji and rich text support
- Cross-platform deployment with JavaFX
- User authentication and account management
- Server-side message queuing for offline delivery

## Lessons Learned
- Socket programming requires careful thread management
- UI responsiveness depends on non-blocking I/O operations
- Custom Swing components enhance user experience
- Exception handling is critical for network applications
- Design patterns simplify complex interaction flows

## Important Notice
This code is protected by copyright and is the intellectual property of Jitesh Kumar.
This project was created for educational purposes to demonstrate:
- Java Socket Programming
- GUI development with Swing
- Real-time communication
- Multi-threading concepts

Any unauthorized use, reproduction, or distribution of this code is strictly prohibited.
For educational references or permissions, please contact the owner.
