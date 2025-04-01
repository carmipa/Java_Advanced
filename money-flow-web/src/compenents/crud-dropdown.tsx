import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import {ChevronDown, Pencil, Trash2} from "lucide-react";

export default function CrudDropdown() {
    return (
        <DropdownMenu>
            <DropdownMenuTrigger>
                <ChevronDown />
            </DropdownMenuTrigger>
            <DropdownMenuContent>
                <DropdownMenuItem>
                    <Pencil />
                    Editar
                </DropdownMenuItem>
                <DropdownMenuItem>
                    <Trash2 />
                    Apagar
                </DropdownMenuItem>
            </DropdownMenuContent>
        </DropdownMenu>
    )
}