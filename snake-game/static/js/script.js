// 타이머 부분
let timerId;
let time = 0;
const stopwatch = document.getElementById("stopwatch");
let  hour, min, sec;

function printTime() {
  time++;
  stopwatch.innerText = getTimeFormatString();
}

//시계 시작 - 재귀호출로 반복실행
function startClock() {
  printTime();
  stopClock();
  timerId = setTimeout(startClock, 1000);
}

//시계 중지
function stopClock() {
  if (timerId != null) {
      clearTimeout(timerId);
  }
}

// 시계 초기화
function resetClock() {
  stopClock()
  stopwatch.innerText = "00:00:00";
  time = 0;
}

// 시간(int)을 시, 분, 초 문자열로 변환
function getTimeFormatString() {
  hour = parseInt(String(time / (60 * 60)));
  min = parseInt(String((time - (hour * 60 * 60)) / 60));
  sec = time % 60;

  return String(hour).padStart(2, '0') + ":" + String(min).padStart(2, '0') + ":" + String(sec).padStart(2, '0');
}


// 게임 부분
const canvas = document.getElementById("game-board");
const ctx = canvas.getContext("2d");
const scale = 20;
const rows = canvas.height / scale;
const columns = canvas.width / scale;
let score = 0;

let snake;
let fruit;
let gameInterval;

(function setup() {
  snake = new Snake();
  fruit = new Fruit();

  fruit.pickLocation();

}());

function startGame() {
  if (!gameInterval) {
    gameInterval = setInterval(() => {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      fruit.draw();
      snake.update();
      snake.draw();

      if (snake.eat(fruit)) {
        score++;
        document.getElementById("score").textContent = score;
        fruit.pickLocation();
      }

      snake.checkCollision();
    }, 250);
  }
}

function stopGame() {
  clearInterval(gameInterval);
  gameInterval = null;
}

function gameOver() {
  alert("Game Over\n점수: " + score + "점  생존시간: "+stopwatch.innerText+"\n점수를 등록하시려면 이름을 입력 후 확인을 눌러주세요.");
  var name = prompt("이름");
  // INSERT INTO snake_game VALUES (DEFAULT, name, score, gametime);
  // 여기 sql구문 추가해서 db에 점수 추가
  var data = name +"%#"+ score +"%#"+ stopwatch.innerText;
  console.log(data);
  snake.reset();
  resetClock();
  stopGame();
  sendVariableToFlask(data);
  alert("등록되었습니다!")
}

function sendVariableToFlask(myVar) {
  var dataToSend = {
    myVar: myVar
  };

  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/insert_score', true);
  xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
  xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
          // 서버에서의 응답 처리
          var response = JSON.parse(xhr.responseText);
          alert(response.message);
      }
  };
  xhr.send(JSON.stringify(dataToSend));
  
}

function score_save() {
  
}

function Snake() {
  this.x = 0;
  this.y = 0;
  this.xMoving = scale;
  this.yMoving = 0;
  this.tail = [];

  this.draw = function () {
    ctx.fillStyle = "#6a4caf";

    for (let i = 0; i < this.tail.length; i++) {
      ctx.fillRect(this.tail[i].x, this.tail[i].y, scale, scale);
    }

    ctx.fillRect(this.x, this.y, scale, scale);
  };

  this.update = function () {
    for (let i = 0; i < this.tail.length - 1; i++) {
      this.tail[i] = this.tail[i + 1];
    }

    this.tail[this.tail.length - 1] = { x: this.x, y: this.y };

    this.x += this.xMoving;
    this.y += this.yMoving;

    if (this.x < 0 || this.y < 0 || this.x >= canvas.width || this.y >= canvas.height) {
      gameOver();
      stopClock();
    }
  };

  this.changeDirection = function (direction) {
    switch (direction) {
      case "Up":
        this.xMoving = 0;
        this.yMoving = -scale;
        break;
      case "Down":
        this.xMoving = 0;
        this.yMoving = scale;
        break;
      case "Left":
        this.xMoving = -scale;
        this.yMoving = 0;
        break;
      case "Right":
        this.xMoving = scale;
        this.yMoving = 0;
        break;
    }
  };

  this.eat = function (fruit) {
    if (this.x === fruit.x && this.y === fruit.y) {
      this.tail.push({ x: this.x - this.xMoving, y: this.y - this.yMoving });
      return true;
    }
    return false;
  };

  this.checkCollision = function () {
    for (let i = 0; i < this.tail.length; i++) {
      if (this.x === this.tail[i].x && this.y === this.tail[i].y) {
        gameOver();
      }
    }
  };

  this.reset = function () {
    this.x = 0;
    this.y = 0;
    this.tail = [];
    this.xMoving = scale;
    this.yMoving = 0;
    score = 0;
    document.getElementById("score").textContent = score;
  };
}

function Fruit() {
  this.x = 0;
  this.y = 0;

  this.pickLocation = function () {
    this.x = Math.floor(Math.random() * columns) * scale;
    this.y = Math.floor(Math.random() * rows) * scale;
  };

  this.draw = function () {
    ctx.fillStyle = "#FF4136";
    ctx.fillRect(this.x, this.y, scale, scale);
  };
}

window.addEventListener("keydown", (event) => {
  const direction = event.key.replace("Arrow", "");
  snake.changeDirection(direction);
});

document.getElementById("start-btn").addEventListener("click", startGame);
document.getElementById("start-btn").addEventListener("click", startClock);

