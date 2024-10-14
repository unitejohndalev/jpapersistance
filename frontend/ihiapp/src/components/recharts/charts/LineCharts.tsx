import { CartesianGrid, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts"
import { chartData } from "../../../mockData/data"


export const LineCharts = () => {
  return (
    <>
          <div className=' w-[45%] border-2 border-yellow-500 border-solid p-5'>
              <div className="section col-md-6 w-full">
                  <h3 className="section-title bg-yellow-500 text-center">Line/S-Curve Chart</h3>
                  <div className="section-content">
                      <ResponsiveContainer width="100%" height={300}>
                          <LineChart data={chartData} margin={{ top: 15, right: 0, bottom: 15, left: 0 }}>
                              <Tooltip
                                  formatter={(value, name) => [ `${name}: ${value} hrs`, '' ]}>
                              </Tooltip>
                              <XAxis dataKey="label" />
                              <YAxis />
                              <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                              <Legend />
                              <Line dataKey="TBMH" fill="#FB8833" />
                              <Line dataKey="MOV" fill="#17A8F5" />
                              <Line dataKey="TBMHUTD" fill="#FBBF24" />
                              <Line dataKey="TSMHUTD" fill="#F87171" />
                              <Line dataKey="SMH" fill="#A78BFA" />
                          </LineChart>
                      </ResponsiveContainer>
                  </div>
              </div>
          </div>
    </>
  )
}
