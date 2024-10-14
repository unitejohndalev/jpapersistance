import { AreaChart, CartesianGrid, XAxis, YAxis, Area, Tooltip, ResponsiveContainer } from "recharts"
import { areaData } from "../../../mockData/data"
import { CustomTooltipProps } from "../../../types/Types";

export const AreaCharts = () => {
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
            <div className='w-[45%] border-2 border-blue-500 border-solid p-5'>
                <h3 className="section-title bg-blue-500 text-center">Area Chart</h3>
                <ResponsiveContainer width="100%" height={300}>
                    <AreaChart data={areaData}>
                        <CartesianGrid />
                        <XAxis dataKey="name" />
                        <YAxis />
                        <Area
                            type="monotone"
                            dataKey="x"
                            stackId="1"
                            stroke="black"
                            fill="black"
                        />
                        <Area
                            type="monotone"
                            dataKey="y"
                            stackId="1"
                            stroke="blue"
                            fill="blue"
                        />
                        <Area
                            type="monotone"
                            dataKey="z"
                            stackId="2"
                            stroke="green"
                            fill="green"
                        />
                        <Tooltip content={<CustomTooltip />} />
                    </AreaChart>
                </ResponsiveContainer>

            </div>
        </>
    )
}
