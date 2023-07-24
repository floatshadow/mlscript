const util = require("util");
const fs = require("fs");
const path = require("path");
const wasmFilePath = path.join(__dirname, "../diff/wasmout/codegen_Statement_1.wasm");
const wasmBinary = fs.readFileSync(wasmFilePath);
const memory = new WebAssembly.Memory({ initial: 100 });

const importObject = {
    system: {
        mem:memory,
        log: function (arg,tpe) {
            switch(tpe){
                //TODO Float, Record, Variant, Function, TypeName
                case 0:
                    console.log("()")
                    break;
                case 1:
                    if(arg==0)
                        console.log("false")
                    else
                        console.log("true")
                    break;
                case 2:
                    console.log(arg)
                    break;
                case 4:
                    var bufView = new Uint8Array(memory.buffer);
                    var i = arg;
                    var result = "";
                    while (bufView[i] != 0) {
                        result += String.fromCharCode(bufView[i]);
                        i = i + 1;
                    }
                    console.log(result)
                    break;
            }
        },
    }
};

var wasmModule = new Uint8Array(wasmBinary);

WebAssembly.instantiate(wasmModule, importObject)