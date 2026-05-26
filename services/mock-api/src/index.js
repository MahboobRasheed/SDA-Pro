const express = require('express');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 8080;

app.use(cors());
app.use(express.json());

let incidents = [
  {
    id: 'inc-001',
    title: 'Suspicious PowerShell Execution',
    severity: 'CRITICAL',
    currentState: 'NEW',
    createdAt: new Date().toISOString()
  },
  {
    id: 'inc-002',
    title: 'Failed Login Attempts from Unknown IP',
    severity: 'HIGH',
    currentState: 'UNDER_TRIAGE',
    createdAt: new Date().toISOString()
  }
];

app.get('/api/incidents', (req, res) => {
  res.json(incidents);
});

app.get('/api/incidents/:id', (req, res) => {
  const incident = incidents.find(i => i.id === req.params.id);
  if (incident) {
    res.json(incident);
  } else {
    res.status(404).json({ error: 'Incident not found' });
  }
});

app.post('/api/incidents/:id/actions', (req, res) => {
  const incident = incidents.find(i => i.id === req.params.id);
  if (incident) {
    incident.currentState = 'CONTAINMENT';
    incident.responseHistory = incident.responseHistory || [];
    incident.responseHistory.push(req.body.actionType);
    res.json({ success: true, incident });
  } else {
    res.status(404).json({ error: 'Incident not found' });
  }
});

app.get('/health', (req, res) => {
  res.json({ status: 'ok', service: 'mock-api' });
});

app.listen(PORT, () => {
  console.log(`Mock API running on port ${PORT}`);
});

