const svg = document.getElementById('plexus');
const width = window.innerWidth;
const height = window.innerHeight;

svg.setAttribute('width', width);
svg.setAttribute('height', height);

const numPoints = 80;
const maxDistance = 150;
const points = [];

class Point {
  constructor() {
    this.x = Math.random() * width;
    this.y = Math.random() * height;
    this.vx = (Math.random() - 0.5) * 1.5;
    this.vy = (Math.random() - 0.5) * 1.5;
    this.opacity = Math.random();
    this.fadeDirection = Math.random() > 0.5 ? 1 : -1;
    this.fadeSpeed = 0.005 + Math.random() * 0.01;
    
    this.element = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    this.element.setAttribute('r', '2');
    this.element.setAttribute('fill', '#6366f1');
    svg.appendChild(this.element);
  }

  update() {
    this.x += this.vx;
    this.y += this.vy;

    if (this.x < 0 || this.x > width) this.vx *= -1;
    if (this.y < 0 || this.y > height) this.vy *= -1;

    this.x = Math.max(0, Math.min(width, this.x));
    this.y = Math.max(0, Math.min(height, this.y));

    this.opacity += this.fadeDirection * this.fadeSpeed;
    if (this.opacity >= 1) {
      this.opacity = 1;
      this.fadeDirection = -1;
    } else if (this.opacity <= 0.1) {
      this.opacity = 0.1;
      this.fadeDirection = 1;
    }

    this.element.setAttribute('cx', this.x);
    this.element.setAttribute('cy', this.y);
    this.element.setAttribute('opacity', this.opacity);
  }
}

for (let i = 0; i < numPoints; i++) {
  points.push(new Point());
}

const lineGroup = document.createElementNS('http://www.w3.org/2000/svg', 'g');
lineGroup.setAttribute('id', 'lines');
svg.insertBefore(lineGroup, svg.firstChild);

function drawConnections() {
  lineGroup.innerHTML = '';

  for (let i = 0; i < points.length; i++) {
    for (let j = i + 1; j < points.length; j++) {
      const dx = points[i].x - points[j].x;
      const dy = points[i].y - points[j].y;
      const distance = Math.sqrt(dx * dx + dy * dy);

      if (distance < maxDistance) {
        const opacity = (1 - distance / maxDistance) * 
                      Math.min(points[i].opacity, points[j].opacity) * 0.5;
        
        const line = document.createElementNS('http://www.w3.org/2000/svg', 'line');
        line.setAttribute('x1', points[i].x);
        line.setAttribute('y1', points[i].y);
        line.setAttribute('x2', points[j].x);
        line.setAttribute('y2', points[j].y);
        line.setAttribute('stroke', '#8b5cf6');
        line.setAttribute('stroke-width', '1');
        line.setAttribute('opacity', opacity);
        lineGroup.appendChild(line);
      }
    }
  }
}

function animate() {
  points.forEach(p => p.update());
  drawConnections();
  requestAnimationFrame(animate);
}

window.addEventListener('resize', () => {
  const newWidth = window.innerWidth;
  const newHeight = window.innerHeight;
  svg.setAttribute('width', newWidth);
  svg.setAttribute('height', newHeight);
});

// Smooth scroll for navigation
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
  anchor.addEventListener('click', function (e) {
    e.preventDefault();
    const target = document.querySelector(this.getAttribute('href'));
    if (target) {
      target.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  });
});

animate();