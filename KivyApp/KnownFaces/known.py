import face_recognition as fr
from PIL import Image
import os

faces=[]
known=[]
un=[]
for filename in os.listdir("Training"):
   fac=fr.load_image_file("Training//"+filename)
   face=fr.face_encodings(fac)[0] 	
   faces.append(face)

for filename in os.listdir("Testing"):
   fac=fr.load_image_file("Testing//"+filename)
   print(filename)
   enco=fr.face_encodings(fac)[0]
   result=fr.compare_faces(faces,enco)
   rset=list(set(result))
   if True in rset:
     print("Contains known face")
     known.append(filename)
   if len(rset)==1 and rset[0]==False:
     print("Unknown Faces")
     un.append(filename)