export type ChartData = {
    label: string;
    TBMH: number;
    MOV: number;
    TBMHUTD:number;
    TSMHUTD:number;
    SMH: number;
    bmdate: string;
    tbmh: number;
    mov: number;
    tbmhutd: number;
    tsmhutd: number;
    smh: number;
}

export type PieData = {
    name: string;
    value: number;
    }

export type AreaData = {
    name: string;
    x: number;
    y: number;
    z: number;
};

export type RadarData = {
    subject: string;
    A: number;
    B: number;
    fullMark: number;
};    

export type ScatterData = {
    x: number;
    y: number;
    z: number;
};



export type CustomTooltipProps = {
    active?: boolean;
    payload?: { name: string; value: number }[];
};


export type UserInput = {
    username: string;
    password: string;
};


export type RegisterInfo = {
    email: string;
    username: string;
    password: string;
};

export type role = {
    role: string
}