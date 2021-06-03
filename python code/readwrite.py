import struct
import os
import cv2
import face_recognition

def call_from():
	faces=[]
	known=[]
	un=[]

	file1 = "C:/Users/hiral/Desktop/asd"
	file = open(file1,'rb')

	#bytecount = 0
	#bytecount += 4
	t = file.read(4)
	count1 = struct.unpack('!i', t)
	count1 = int(count1[0])
	print(count1)

	t = file.read(4)
	count2 = struct.unpack('!i', t)
	count2 = int(count2[0])
	print(count2)
	# t = file.read(4)
	# count = struct.unpack('!i', t)
	# print(count)

	i = 0
	while i<count1:
		i = i+1
		t = file.read(4)
		size = struct.unpack('!i',t)
		size = int(size[0])
		print(size)

		t = file.read(4)
		length = struct.unpack('!i',t)
		length = int(length[0])
		print(length)
		
		t = file.read(4)
		type1 = struct.unpack('!i',t)
		type1 = int(type1[0])
		print(type1)

		filename = ''
		ii = 0
		ch = ''
		while ii<length:
			t = file.read(1)
			ch = struct.unpack('!c', t)
			filename += ''.join(ch)
			ii = ii + 1
		print(filename)	

		file2 = "C:/Users/hiral/Desktop/Training/"+filename;
		with open(file2, 'wb') as img:
			data = file.read(size)
			if not data:
				break
			img.write(data)
		print('received, yay!')
		print(os.path.isfile(file2))	

		fac = cv2.imread(file2)
		#print(image)
		#cv2.imshow("image",image)
		print(len(face_recognition.face_encodings(fac)))
		#print()
		if(len(face_recognition.face_encodings(fac))>0):
		  face=face_recognition.face_encodings(fac)[0]  
		  faces.append(face)
		else:
		  pass
  	print(len(faces))

	j = 0
	while j<count2:
		j = j+1
		t = file.read(4)
		size = struct.unpack('!i',t)
		size = int(size[0])
		print(size)

		t = file.read(4)
		length = struct.unpack('!i',t)
		length = int(length[0])
		print(length)
		

		t = file.read(4)
		type1 = struct.unpack('!i',t)
		type1 = int(type1[0])
		print(type1)
		

		filename = ''
		ii = 0
		ch = ''
		while ii<length:
			t = file.read(1)
			ch = struct.unpack('!c', t)
			filename += ''.join(ch)
			ii = ii + 1
		print(filename)	

		file2 = "C:/Users/hiral/Desktop/Testing/"+filename;
		with open(file2, 'wb') as img:
			data = file.read(size)
			if not data:
				break
			img.write(data)
		print('received, yay!')
		print(os.path.isfile(file2))
		fac=cv2.imread("C:\Users\hiral\Desktop\Testing\\"+filename)
		print(filename)
		#print(len(fac))
		abc = face_recognition.face_encodings(fac)
		#f1 = fac[0]
		print("No of encodings...",len(abc))
		if(len(abc)>0):
			enco=face_recognition.face_encodings(fac)[0]  
			result=face_recognition.compare_faces(faces,enco)

			rset=list(set(result))

			print(rset)
			if True in rset:
				print("Contains known face")

				known.append(filename)
			if len(rset)==1 and rset[0]==False:
				print("Contains Unknown Faces")
				un.append(filename)
			#np.append(faces, face)
		else:
			pass

	return known

