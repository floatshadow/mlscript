(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $a i32)(local $z1 i32)(local $o1 i32)(local $x1 i32)(local $h i32)(local $w i32)(local $tmp9 i32)(local $k i32)(local $e i32)(local $l1 i32)(local $n i32)(local $res1 i32)(local $u1 i32)(local $t i32)(local $f1 i32)(local $q i32)(local $b i32)(local $y1 i32)(local $r1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
      i32.store
      global.get 0
      i32.const 8
      i32.add
      global.set 0
      local.get $a
      i32.const 4
      i32.add
      i32.const 1
      i32.store
      local.get $a
      local.set $x1
      block $d
        block $s
          block $v
            block $g
              block $Match_x1
                local.get $x1
                i32.load
                i32.const 1
                i32.eq
                i32.const 1
                i32.mul
                local.get $x1
                i32.load
                i32.const 2
                i32.eq
                i32.const 2
                i32.mul
                i32.add
                local.get $x1
                i32.load
                i32.const 0
                i32.eq
                i32.const 3
                i32.mul
                i32.add
                br_table 3 2 1 0
              end
              local.get $x1
              local.set $tmp9
              local.get $tmp9
              i32.const 4
              i32.add
              i32.load
              local.set $h
              local.get $h
              local.set $x1
              block $j
                block $p
                  block $m
                    block $Match_x1
                      local.get $x1
                      i32.const 1
                      i32.eq
                      i32.const 1
                      i32.mul
                      local.get $x1
                      i32.const 0
                      i32.eq
                      i32.const 2
                      i32.mul
                      i32.add
                      br_table 2 1 0
                    end
                    i32.const 0
                    call $log
                    i32.const 0
                    local.set $o1
                    local.get $o1
                    local.set $n
                    local.get $n
                    local.set $b
                    br $j
                  end
                  i32.const 1
                  call $log
                  i32.const 0
                  local.set $r1
                  i32.const 3
                  local.set $q
                  local.get $q
                  local.set $b
                  br $j
                end
                i32.const 40
                call $log
                i32.const 0
                local.set $l1
                local.get $l1
                local.set $k
                local.get $k
                local.set $b
              end
              br $d
            end
            i32.const 30
            call $log
            i32.const 0
            local.set $y1
            local.get $y1
            local.set $w
            local.get $w
            local.set $b
            br $d
          end
          i32.const 20
          call $log
          i32.const 0
          local.set $u1
          local.get $u1
          local.set $t
          local.get $t
          local.set $b
          br $d
        end
        i32.const 40
        call $log
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
    local.get $b
    local.set $res1
    local.get $res1
    call $log
    i32.const 0
    local.set $z1
  )
  (start $main)
)