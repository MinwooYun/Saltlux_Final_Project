from flask import Flask, render_template, request, jsonify, make_response
import tensorflow as tf
import re
import tensorflow_datasets as tfds
import pandas as pd
import base64
import numpy as np
import cv2

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/results', methods=['GET'])
def get_results():
    print(request.method)

    data = request.args.get('search', 'utf-8')
    print(data)

    response = make_response(jsonify(data))
    response.headers.add("Access-Control-Allow-Origin", "*")
    return response

@app.route('/test_img', methods=['POST'])
def rest_img_test():
    param = request.form.get('data')
    f = request.files['file']
    fileStr = f.read()
    npimg = np.frombuffer(fileStr, np.uint8)
    img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    cv2.imwrite(f.filename, img_gray)
    #영상처리, 객체 탐지
    #[1]이 인코딩된 문자열
    img_str = base64.b64encode(cv2.imencode(".jpg", img_gray)[1]).decode()
    data = {"param" : param, "file" : img_str}
    response = make_response(jsonify(data))
    response.headers.add("Access-Control-Allow-Origin", "*")
    return response

if __name__ == '__main__':
    app.debug = True
    app.run()
