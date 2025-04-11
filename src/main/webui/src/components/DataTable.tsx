import { ScrollArea } from "@base-ui-components/react/scroll-area";
import type { FC } from "react";

const Area: FC<{ children: string; }> = ({ children }) => {
    return (
        <ScrollArea.Root className="h-full w-full overflow-auto">
            <ScrollArea.Viewport>
                <pre className="bg-black-400 text-white-600 rounded-lg p-2 font-mono whitespace-pre overflow-auto">
                    {children}
                </pre>
            </ScrollArea.Viewport>
            <ScrollArea.Scrollbar>
                <ScrollArea.Thumb />
            </ScrollArea.Scrollbar>
            <ScrollArea.Corner />
        </ScrollArea.Root>
    )
}

export const DataTable: FC<{ json: string; }> = ({ json }) => {
    try {
        const block = JSON.stringify(JSON.parse(json), null, 4)
        return <Area>{block}</Area>
    } catch (e) {
        return <Area>
            {`Invalid JSON: ${json}\nError while parsing: ${e}`}
        </Area>
    }
};
