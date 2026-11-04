import http from 'k6/http';
import { check,sleep } from 'k6';

export const options = {
   stages: [
    { duration: '1m', target: 5 },   // Baseline (Warm-up)
    { duration: '2m', target: 20 },  // Moderate Load
    { duration: '2m', target: 50 },  // High Load
    { duration: '2m', target: 100 }, // Stress / Saturation Test
    { duration: '1m', target: 0 },   // Cool-down (Recovery test)
  ]
};

export default function () {
    const response = http.get(
        'http://localhost:8080/api/customers/1'
    );

    check(response, {
        'status is 200': (r) => r.status === 200,
    });
sleep(0.1);
}
