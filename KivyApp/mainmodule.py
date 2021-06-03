from kivy.app import App
from kivy.uix.widget import Widget
from kivy.uix.boxlayout import BoxLayout 
from kivy.uix.floatlayout import FloatLayout 
from kivy.uix.gridlayout import GridLayout
from kivy.uix.scrollview import ScrollView
from kivy.uix.image import Image as img
from kivy.uix.screenmanager import Screen
from kivy.core.window import Window
from kivy.uix.carousel import Carousel
from skimage.measure import compare_ssim as ssim
from matplotlib import pyplot as plt
from PIL import Image

import numpy as np
import cv2
import os, itertools
import pytesseract
import glob
import webbrowser

class KivyRoot(BoxLayout):
	"""docstring for KivyRoot"""
	i1 = 0
	i2 = 0
	sc = 0
	def __init__(self, **kwargs):
		super(KivyRoot, self).__init__(**kwargs)
		self.screen_list = []

	def changeScreen(self, next_screen):
		
		if self.ids.screen_manager.current not in self.screen_list:
			self.screen_list.append(self.ids.screen_manager.current)

		if next_screen == 'about':
			self.ids.screen_manager.current = 'about_screen'
		
		if next_screen == 'duplicateimages':
			pass
		
		if next_screen == 'textimages':
			pass
			
	def onBack(self):
		if self.screen_list:
			self.ids.screen_manager.current = self.screen_list.pop()
			return True
		return False	

class KivyApp(App):
	"""docstring for KivyApp"""
	def __init__(self, **kwargs):
		super(KivyApp, self).__init__(**kwargs)
		Window.bind(on_keyboard = self.onBack)


	def onBack(self,window,key,*args):
		if key == 27:
			return self.root.onBack()

	


	def getText(self):
		return ("This app was built using Kivy and Python..."
				"References:"
				"[b][ref=kivy]Kivy[/ref][/b]"
				"[b][ref=python]Python[/ref][/b]")

	def on_ref_press(self, instance, ref):
		dictt = {
			'kivy': 'https://kivy.org/#home',
			'python': 'https://www.python.org/'
		}
		webbrowser.open(dictt[ref])


	def build(self):
		return KivyRoot()


if __name__ == '__main__':
	carousel = Carousel(direction='left')
	#my = Image_Thumb(size_hint_y=None)
	scroll = ScrollView()
	face_cascade = cv2.CascadeClassifier('C:\Python27\Lib\site-packages\cv2\data\haarcascade_frontalface_default.xml')
	profile_cascade = cv2.CascadeClassifier('C:\Python27\Lib\site-packages\cv2\data\haarcascade_profileface.xml')
	ll=[]
	ll1 = []
	ll2 = []
	ll3 = []
	KivyApp().run()
		

