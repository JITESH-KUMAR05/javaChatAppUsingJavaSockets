# Java Chat Application

A real-time chat application built using Java Swing and Socket Programming.

## Ownership & Disclaimer
This project is created and owned by Jitesh Kumar.
This is an educational project and is intended for learning purposes only.
Please do not use this code in your projects without proper permission.

© 2024 Jitesh Kumar. All Rights Reserved.

## Features

- Real-time messaging between two users
- Modern UI with profile pictures and status
- Message timestamps
- Color-coded messages (sent vs received)
- Active status indicators
- Minimalist design

## Technical Stack

- Java SE 22
- Java Swing for GUI
- Socket Programming for real-time communication
- Multi-threading for non-blocking operations

## Project Structure 
chatapp/
├── src/
│ ├── Server.java
│ └── Client.java
└── icons/
├── 1.png (Server profile)
├── 2.png (Client profile)
├── 3.png (Back button)
├── 3icon.png (More options)
├── video.png
└── phone.png

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

## Features Explanation

### Server Side
- Listens on port 6001
- Handles incoming connections
- Displays messages in default theme color
- Shows timestamp for each message

### Client Side
- Connects to localhost:6001
- Matches server's functionality
- Different profile picture
- Complementary color scheme for received messages

## Message Format
- Sent messages: Right-aligned, default theme color (pink)
- Received messages: Left-aligned, complementary color
- Timestamps: Below each message
- Dynamic width based on message length

## Implementation Details

### GUI Components
- JFrame for main window
- Custom panels for messages
- BorderLayout for message alignment
- BoxLayout for vertical message stacking

### Networking
- ServerSocket for server
- Socket for client connection
- DataInputStream/DataOutputStream for message transfer
- Threading for non-blocking operations

## Known Limitations
- Supports only two users
- Runs on localhost only
- No message persistence
- No encryption

## Future Improvements
- Add multiple user support
- Implement message database
- Add file sharing
- Add encryption
- Add emoji support

## Important Notice
This code is protected by copyright and is the intellectual property of Jitesh Kumar.
This project was created for educational purposes to demonstrate:
- Java Socket Programming
- GUI development with Swing
- Real-time communication
- Multi-threading concepts

Any unauthorized use, reproduction, or distribution of this code is strictly prohibited.
For educational references or permissions, please contact the owner.

