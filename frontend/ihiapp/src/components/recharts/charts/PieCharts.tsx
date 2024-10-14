import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { pieData } from "../../../mockData/data";
import { CustomTooltipProps } from "../../../types/Types";


 
export const PieCharts = () => {
    const COLORS = [ "#8884d8", "#82ca9d", "#FFBB28", "#FF8042", "#AF19FF" ];
    const CustomTooltip = ({ active, payload }: CustomTooltipProps) => {
        
        if (active && payload && payload.length > 0) {
            return (
                <div
                    className="custom-tooltip"
                    style={{
                        backgroundColor: "#ffff",
                        padding: "5px",
                        border: "1px solid #cccc"
                    }}
                >
                    <label>{`${payload[ 0 ].name} : ${payload[ 0 ].value}%`}</label>
                </div>
            );
        }
        return null;
    };
  return (
    <>
          <div className='w-[45%] border-2 border-orange-500 border-solid p-5'>
              <h3 className="section-title bg-orange-500 text-center">Pie Chart</h3>
        <ResponsiveContainer width="100%" height={300}>
          <PieChart >
              <Pie
                  data={pieData}
                  dataKey="value"
                  nameKey="name"
                  cx="50%"
                  cy="50%"
                  outerRadius={120}
                  fill="#8884d8"
              >
                  {pieData.map((_entry, index) => (
                      <Cell
                          key={`cell-${index}`}
                          fill={COLORS[ index % COLORS.length ]}
                      />
                  ))}
              </Pie>
              <Tooltip content={<CustomTooltip />} />
              <Legend />
          </PieChart>
        </ResponsiveContainer>
          </div>
    </>
  )
}

