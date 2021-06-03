from skimage.measure import compare_ssim as ssim
from matplotlib import pyplot as plt
import numpy as np
import cv2
import os, itertools
from PIL import Image

#function to calculate mse
def mse(image1, image2):
	error = np.sum((image1.astype("float")-image2.astype("float"))**2)
	error /= float(image1.shape[0]*image1.shape[1])
	return error

#function to calculate mse, ssim
def findDuplicate(image1, image2):
	meanSquareError = mse(image1, image2)
	structuralSimilarity = ssim(image1, image2)
	return meanSquareError, structuralSimilarity

#initialize
files = os.listdir("C:\Users\hiral\Desktop\project\duplicate\DatasetDup")
numberOfFiles = len(files)
count = 0
i = 0
j = 1
mselist = [[0 for x in range(numberOfFiles)] for y in range(numberOfFiles)]
ssimlist = [[0 for x in range(numberOfFiles)] for y in range(numberOfFiles)]

#read 2 images
#resize images
#convert to grayscale
#calculate mse and ssim
print(files[1])
for file1, file2 in itertools.combinations(files, 2): 
	print file1, file2, ++count
	#file1 = "jura1.png"
	#file2 = "jura2.jpeg"
	image1 = cv2.imread("C:\Users\hiral\Desktop\project\duplicate\DatasetDup\\"+file1)
	image2 = cv2.imread("C:\Users\hiral\Desktop\project\duplicate\DatasetDup\\"+file2)
	#if image1:
	image1 = cv2.resize(image1, (300, 300))
	image1 = cv2.cvtColor(image1, cv2.COLOR_BGR2GRAY)
	#if image2:
	image2 = cv2.resize(image2, (300, 300))
	image2 = cv2.cvtColor(image2, cv2.COLOR_BGR2GRAY)
	meanSquareError, structuralSimilarity = findDuplicate(image1, image2)
	print("MeanSquareError... ", meanSquareError)
	print("SSIM... ", structuralSimilarity)
	mselist[i][j] = meanSquareError
	ssimlist[i][j] = structuralSimilarity
	if j == (numberOfFiles-1):
		i = i+1
		j = i+1
	else:
		j = j+1

#matrix for mse
for i in range(numberOfFiles):
	for j in range(numberOfFiles):
		print(mselist[i][j]),
	print()

#matrix for ssim
for i in range(numberOfFiles):
	for j in range(numberOfFiles):
		print(ssimlist[i][j]),
	print()

c = 0
#cv2.imwrite("C:\Users\hiral\Desktop\duplicate\abc.png",image1)
#cv2.imwrite("C:\Users\hiral\Desktop\duplicate\ab.png",image2)
#example of border
for i in range(numberOfFiles):
	for j in range(i+1,numberOfFiles):
		if mselist[i][j]<4000 or ssimlist[i][j]>0.5:
			print(mselist[i][j])
			print(ssimlist[i][j])
			image1 = cv2.imread('C:\Users\hiral\Desktop\project\duplicate\DatasetDup\\'+files[i])
			#image1 = Image.open('C:\Users\hiral\Desktop\duplicate\DatasetDup\\'+files[0])
			#cv2.imwrite("abc.jpg",image1)
			image2 = cv2.imread('C:\Users\hiral\Desktop\project\duplicate\DatasetDup\\'+files[j])
			image1 = cv2.copyMakeBorder(image1,10,10,10,10,cv2.BORDER_CONSTANT,value=c)
			image2 = cv2.copyMakeBorder(image2,10,10,10,10,cv2.BORDER_CONSTANT,value=c)
			path = 'C:\Users\hiral\Desktop\project\duplicate'
			cv2.imwrite(os.path.join(path , 'a'+str(c)+'.png'), image1)
			cv2.imwrite(os.path.join(path , 'b'+str(c)+'.png'), image2)
			#cv2.imwrite("C:\Users\hiral\Desktop\duplicate\abc.png",image1)
			#cv2.imwrite("C:\Users\hiral\Desktop\duplicate\ab.png",image2)
			c = c + 1




'''
Work to do... add logic of finding duplicates of each other using matrix and put diff color borders on such duplicates 
'''