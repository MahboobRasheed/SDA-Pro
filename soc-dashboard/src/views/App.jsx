import React, { useState, useEffect } from 'react'

function App() {
  const [incidents, setIncidents] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchIncidents()
  }, [])

  const fetchIncidents = async () => {
    try {
      const response = await fetch('http://localhost:8082/api/incidents')
      if (!response.ok) throw new Error('Failed to fetch')
      const data = await response.json()
      setIncidents(data)
      setError(null)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleRespond = async (incidentId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/incidents/${incidentId}/actions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ actionType: 'ISOLATE' })
      })
      if (response.ok) {
        fetchIncidents()
      }
    } catch (err) {
      console.error('Response failed:', err)
    }
  }

  if (loading) return <div className="loading">Loading SOC Dashboard...</div>
  if (error) return <div className="error">Error: {error}<br/><button onClick={fetchIncidents}>Retry</button></div>

  const stats = {
    total: incidents.length,
    critical: incidents.filter(i => i.severity === 'CRITICAL').length,
    high: incidents.filter(i => i.severity === 'HIGH').length,
    new: incidents.filter(i => i.currentState === 'NEW').length
  }

  return (
    <div className="dashboard">
      <h1>🛡️ SDA-Pro SOC Dashboard</h1>
      
      <div className="stats">
        <div className="stat-card">
          <h3>Total Incidents</h3>
          <div className="number">{stats.total}</div>
        </div>
        <div className="stat-card">
          <h3>Critical</h3>
          <div className="number" style={{color: '#dc3545'}}>{stats.critical}</div>
        </div>
        <div className="stat-card">
          <h3>High</h3>
          <div className="number" style={{color: '#fd7e14'}}>{stats.high}</div>
        </div>
        <div className="stat-card">
          <h3>New Alerts</h3>
          <div className="number" style={{color: '#007bff'}}>{stats.new}</div>
        </div>
      </div>

      <div className="incident-list">
        <h2>📋 Incident Queue</h2>
        {incidents.map(incident => (
          <div key={incident.id} className={`incident-card severity-${incident.severity}`}>
            <strong>{incident.title}</strong>
            <span style={{float: 'right'}}>
              <span style={{background: '#eee', padding: '2px 8px', borderRadius: '3px'}}>
                {incident.currentState}
              </span>
            </span>
            <br/>
            <small>Severity: {incident.severity} | ID: {incident.id}</small>
            <br/>
            <button onClick={() => handleRespond(incident.id)}>🚨 Respond</button>
          </div>
        ))}
      </div>
    </div>
  )
}

export default App