from flask import Flask, request, render_template, redirect
import pymysql
import math
import requests
import json

from datetime import datetime

app = Flask(__name__)

db = pymysql.connect(host='203.234.62.167', port=3308, user='root', passwd='1234', db='2023_KDT')

cursor = db.cursor()



@app.route("/")
def play():
    return render_template("play.html")

@app.route('/score_board', methods = ['POST', 'GET'])
def score_board():

    sql = "SELECT * FROM snake_game ORDER BY score DESC, survivor_time DESC limit 10;"
    cursor.execute(sql)
    score_board = cursor.fetchall()

    name = []
    score = []
    surv_time = []
    inst_time = []
    for i in score_board:
        name.append(i[1])
        score.append(i[2])
        surv_time.append(i[3])
        inst_time.append(i[4])

    return render_template("score_board.html", name=name, score=score, surv_time=surv_time, inst_time=inst_time)

@app.route('/insert_score', methods = ['POST', 'GET'])
def insert_score():
    data = request.get_json()
    my_var = data.get('myVar')  # 전송된 변수 추출
    # 변수 처리 코드
    print("받은 변수:", my_var)

    r_data = my_var.split("%#")
    now = datetime.now()
    
    sql = 'INSERT INTO snake_game VALUES (DEFAULT,"'+r_data[0]+'","'+r_data[1]+'","'+r_data[2]+'","'+now.strftime('%H:%M:%S(')+now.strftime('%Y')[2:]+now.strftime('%m%d)')+'");'
    print(sql)

    try:
        cursor.execute(sql)
        db.commit()
        print("등록완료!")
    except:
        print("fail..")

    return render_template("play.html")

if __name__ == '__main__':
    # app.debug = True
    app.run(host="0.0.0.0", port="8081")
