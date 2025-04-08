import { ScrollArea } from "@base-ui-components/react/scroll-area";
import type { FC } from "react";

export const DataTable: FC<{ json: string; }> = ({ json }) => {
    return <ScrollArea.Root className="h-full">
        <ScrollArea.Viewport>
            <pre>
                {JSON.stringify(JSON.parse(json), null, 4)}
            </pre>
        </ScrollArea.Viewport>
        <ScrollArea.Scrollbar>
            <ScrollArea.Thumb />
        </ScrollArea.Scrollbar>
        <ScrollArea.Corner />
    </ScrollArea.Root>;
};
