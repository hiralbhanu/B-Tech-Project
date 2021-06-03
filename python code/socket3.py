import socket
import struct
import cv2
import numpy as np
import face_recognition
import os
import readwrite

faces=[]
known=[]
un=[]

#HOST = "192.168.56.1" #local host
HOST = "192.168.43.131"
PORT1 = 40132 #open port 7000 for connection
PORT2= 50000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print(HOST)
s.bind((HOST, PORT1))
s.listen(1) #how many connections can it receive at one time
conn, addr = s.accept() #accept the connection
print "Connected by: " , addr #print the address of the person connected

print 'got connected from', addr

filename = 'asd'
file = "C:/Users/hiral/Desktop/"+filename
with open(file, 'wb') as hiral:
	while True:
		data = conn.recv(102400)
		if not data:
			break
		hiral.write(data)

known = readwrite.call_from();
print(known)
print(len(known))
#val = struct.pack('!i',len(known))
conn.send(str(len(known)))
conn.send('!')

for file in known:
	print(file)
	conn.send(file)
	conn.send('!')